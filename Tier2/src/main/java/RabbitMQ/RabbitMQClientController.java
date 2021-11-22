package RabbitMQ;

import com.google.gson.Gson;
import com.rabbitmq.client.*;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class RabbitMQClientController implements RabbitMQClient {
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
                    Message message = gson.fromJson(jsonMessage, Message.class);
                    System.out.println(" [.] message(" + message + ")");

                    switch (message.getAction()) {
                        case "Move":                        
                            Message toSend = new Message();
                            Position toMove = gson.fromJson(message.getData(),Position.class);
                            ChessPiece movedChessPiece = model.MoveChessPiece(toMove.getVerticalAxis(), toMove.getHorizontalAxis());
                            if (movedChessPiece != null) {
                                toSend.setData(gson.toJson(movedChessPiece));
                                toSend.setDataSlot2(gson.toJson(model.getRemovedChessPieces()));
                                toSend.setDataSlot3(model.getMatchScores(true) + " " + model.getMatchScores(false));
                                toSend.setAction("Sending A chess Piece");
                            } else {
                                toSend.setAction("No chess Piece");
                            }
                            System.out.println(toSend.getObject());
                            response = gson.toJson(toSend);
                            break;
                        case "Upgrade":
                            Message toSendUpgrade = new Message();
                            toSendUpgrade.setAction("Upgrade Chess Piece");
                            ChessPiece upgradedChessPiece = model.UpgradeChessPiece(message.getData());
                            toSendUpgrade.setData(gson.toJson(upgradedChessPiece));
                            System.out.println(toSendUpgrade.getObject());
                            response = gson.toJson(toSendUpgrade);
                            break;
                        case "Load":
                            Message toLoadChessPieces = new Message();
                            toLoadChessPieces.setAction("Load ChessBoard");
                            ChessPiece[][] chessBoard = model.getChessBoard();
                            toLoadChessPieces.setData(gson.toJson(chessBoard));
                            toLoadChessPieces.setDataSlot2(gson.toJson(model.getRemovedChessPieces()));
                            toLoadChessPieces.setDataSlot3(model.getMatchScores(true) + " " +model.getMatchScores(false));
                            System.out.println(toLoadChessPieces.getObject());
                            response = gson.toJson(toLoadChessPieces);
                            break;
                        case "Register":
                            User user = gson.fromJson(message.getData(), User.class);
                            String result = model.registerUser(user.getUsername(), user.getPassword(), user.getEmail());
                            response = gson.toJson(new Message(result));
                            break;
                        case "Create challenge":
                            String challengeJson = message.getData();
                            Challenge challenge = gson.fromJson(challengeJson,Challenge.class);
                            result = model.validateChallenge(challenge);
                            response = gson.toJson(new Message(result));
                            break;
                        case "Get challenges":
                            ArrayList<Challenge> challenges;
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
                        case "Login":
                            user = gson.fromJson(message.getData(), User.class);
                            String username = user.getUsername();
                            String password = user.getPassword();
                            try{
                              User validatedUser = model.validateLogin(username, password);
                              String userToJson = gson.toJson(validatedUser);
                              response = gson.toJson(new Message("LoggedIn", userToJson));
                            }
                            catch(Exception e){
                              response = gson.toJson(new Message(e.getMessage()));
                            }
                            break;
                        case "UpdateUser":
                            user = gson.fromJson(message.getData(), User.class);
                            try{
                                User updatedUser = model.updateUser(user);
                                String userToJson = gson.toJson(updatedUser);
                                response = gson.toJson(new Message("UserUpdated", userToJson));
                            }catch(Exception e){
                                response = gson.toJson(new Message(e.getMessage()));
                            }
                        default:
                            break;
                    }
                    System.out.println("Response "+response);

                } catch (RuntimeException e) {
                    System.out.println(" [.] " + e.toString());
                    e.printStackTrace();
                } finally {
                    channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, response.getBytes("UTF-8"));
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    // RabbitMq consumer worker thread notifies the RPC server owner thread
                    synchronized (monitor) {
                        monitor.notify();
                    }
                }
            };

            channel.basicConsume(RPC_QUEUE_NAME, false, deliverCallback, (consumerTag -> {
            }));
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
