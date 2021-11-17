package RabbitMQ;

import com.google.gson.Gson;
import com.rabbitmq.client.*;
import model.Challenge;
import model.Message;
import model.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class RabbitMQClientController implements RabbitMQClient{
    private static final String RPC_QUEUE_NAME = "rpc_queue";
    private Model model;
    private ConnectionFactory factory;
    private Gson gson;

    public RabbitMQClientController(Model model) {
        this.model = model;
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        gson = new Gson();
    }

    @Override
    public void initRPCQueue() throws IOException, TimeoutException {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
            channel.queuePurge(RPC_QUEUE_NAME);

            channel.basicQos(1);

            System.out.println(" [x] Awaiting RPC requests");

            Object monitor = new Object();
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                        .Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                String response = "";

                try {
                    String jsonMessage = new String(delivery.getBody(), "UTF-8");
                    Message message = gson.fromJson(jsonMessage,Message.class);

                    System.out.println(" [.] message(" + message + ")");
                    switch (message.getAction()) {
                        case "Register":
                            String result = model.registerUser(message.getUsername(), message.getPassword(), message.getEmail());
                            response = gson.toJson(new Message(result, message.getUsername(), message.getPassword(), message.getEmail()));
                            break;
                        case "Create challenge":
                            String challengeJson = message.getData();
                            Challenge challenge = gson.fromJson(challengeJson,Challenge.class);
                            result = model.validateChallenge(challenge);
                            response = gson.toJson(new Message(result));
                            break;
                        case "Get challenges":
                            ArrayList<Challenge> challenges = new ArrayList<>();
                            if (message.getData() == null){
                                challenges = model.loadChallenges();
                            }
                            else {
                                challenges = model.loadChallenges(message.getData());
                            }
                            String jsonChallenges = gson.toJson(challenges);
                            response = gson.toJson(new Message("Returning challenges",jsonChallenges));
                            break;
                        case "Accept challenge":
                            challenge = gson.fromJson(message.getData(),Challenge.class);
                            if (model.acceptChallenge(challenge)){
                                response = gson.toJson(new Message("Success"));
                            }
                            else {
                                response = gson.toJson(new Message("Fail"));
                            }

                            break;
                        case "Reject challenge":
                            challenge = gson.fromJson(message.getData(),Challenge.class);
                            if (model.rejectChallenge(challenge)){
                                response = gson.toJson(new Message("Success"));
                            }
                            else{
                                response = gson.toJson(new Message("Fail"));
                            }

                            break;
                        default:
                            break;
                    }
                    System.out.println("Response "+response);
//                    switch (message.getAction().toLowerCase()){
//                        case"register": System.out.println(" [x] Received '" + "Action: " + message.getAction() + " Username: " + message.getUsername() + " Password: " + message.getPassword()
//                                + " Email: " + message.getEmail() + "'");
//
//                            if (tier2.registerUser(message.getUsername(), message.getPassword(), message.getEmail())) {
//                                response = gson.toJson(new Message("Registered successfully", message.getUsername(), message.getPassword(), message.getEmail()));
//                            } else {
//                                // THIS IS WRONG, should be handling some kind of exception that describes why the registration went wrong.
//                                Message message1 = new Message();
//                                message1.setAction("Failed to register");
//                                response = gson.toJson(message1);
//                            }
//
//                            break;
//                        default:
//                            System.out.println("User selected an invalid action");
//                    }
//                    message.setAction(message.getAction().toUpperCase());
//                    response += gson.toJson(message);
                } catch (RuntimeException e) {
                    System.out.println(" [.] " + e.toString());
                } finally {
                    channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, response.getBytes("UTF-8"));
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    // RabbitMq consumer worker thread notifies the RPC server owner thread
                    synchronized (monitor) {
                        monitor.notify();
                    }
                }
            };

            channel.basicConsume(RPC_QUEUE_NAME, false, deliverCallback, (consumerTag -> { }));
            // Wait and be prepared to consume the message from RPC client.
            while (true) {
                synchronized (monitor) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}