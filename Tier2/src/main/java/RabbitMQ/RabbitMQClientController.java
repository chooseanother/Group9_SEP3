package RabbitMQ;

import com.google.gson.Gson;
import com.rabbitmq.client.*;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

/**
 * @author group9
 * @version 1.0
 */

public class RabbitMQClientController implements RabbitMQClient {
    private static final String RPC_QUEUE_NAME = "rpc_queue";
    private Model model;
    private ConnectionFactory factory;
    private Gson gson;

    /**
     * Creates the controller
     * @param model model
     */
    public RabbitMQClientController(Model model) {
        this.model = model;
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        gson = new Gson();
    }

    /**
     * Checks the queue
     * @throws IOException Io exception
     * @throws TimeoutException timeout
     */
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
                    int matchID = 0;
                    if (message.getAction().equals("Move") || message.getAction().equals("Load") || message.getAction().equals("Upgrade")) {
                        matchID = Integer.parseInt(message.getDataSlot2());
                    }
                    switch (message.getAction()) {
                        case "Move":
                            Message toSend = new Message();
                            ChessPiece selected = gson.fromJson(message.getData(), ChessPiece.class);
                            ChessPiece movedChessPiece = model.moveChessPiece(selected, matchID, message.getDataSlot3());
                            if (movedChessPiece != null) {
                                toSend.setData(gson.toJson(movedChessPiece));
                                toSend.setDataSlot2(gson.toJson(model.getRemovedChessPieces(matchID)));
                                toSend.setDataSlot3(model.getMatchScores(true, matchID) + " " + model.getMatchScores(false, matchID));
                                toSend.setAction("Sending A chess Piece");
                            } else {
                                toSend.setAction("No chess Piece");
                            }

                            System.out.println(toSend.getData());

                            response = gson.toJson(toSend);
                            break;
                        case "Upgrade":
                            Message toSendUpgrade = new Message();
                            toSendUpgrade.setAction("Upgrade Chess Piece");
                            ChessPiece toUpgrade = gson.fromJson(message.getDataSlot3(), ChessPiece.class);
                            ChessPiece upgradedChessPiece = model.upgradeChessPiece(message.getData(), toUpgrade, matchID, message.getDataSlot4());
                            toSendUpgrade.setData(gson.toJson(upgradedChessPiece));

                            System.out.println(toSendUpgrade.getData());

                            response = gson.toJson(toSendUpgrade);
                            break;
                        case "Load":
                            Message toLoadChessPieces = new Message();
                            toLoadChessPieces.setAction("Load ChessBoard");
                            ChessPiece[][] chessBoard = model.getChessBoard(matchID).getChessBoard();
                            toLoadChessPieces.setData(gson.toJson(chessBoard));
                            toLoadChessPieces.setDataSlot2(gson.toJson(model.getRemovedChessPieces(matchID)));
                            toLoadChessPieces.setDataSlot3(model.getMatchScores(true, matchID) + " " + model.getMatchScores(false, matchID));
                            response = gson.toJson(toLoadChessPieces);
                            break;
                        case "Register":
                            User user = gson.fromJson(message.getData(), User.class);
                            String result = model.registerUser(user.getUsername(), user.getPassword(), user.getEmail());
                            response = gson.toJson(new Message(result));
                            break;
                        case "Create challenge":
                            String challengeJson = message.getData();
                            Challenge challenge = gson.fromJson(challengeJson, Challenge.class);
                            result = model.validateChallenge(challenge);
                            response = gson.toJson(new Message(result));
                            break;
                        case "Get challenges":
                            ArrayList<Challenge> challenges;
                            if (message.getData() == null) {
                                challenges = model.loadChallenges();
                            } else {
                                challenges = model.loadChallenges(message.getData());
                            }
                            String jsonChallenges = gson.toJson(challenges);
                            response = gson.toJson(new Message("Returning challenges", jsonChallenges));
                            break;
                        case "Accept challenge":
                            challenge = gson.fromJson(message.getData(), Challenge.class);
                            if (model.acceptChallenge(challenge)) {
                                response = gson.toJson(new Message("Success"));
                            } else {
                                response = gson.toJson(new Message("Fail"));
                            }
                            break;
                        case "Reject challenge":
                            challenge = gson.fromJson(message.getData(), Challenge.class);
                            if (model.rejectChallenge(challenge)) {
                                response = gson.toJson(new Message("Success"));
                            } else {
                                response = gson.toJson(new Message("Fail"));
                            }

                            break;
                        case "Login":
                            user = gson.fromJson(message.getData(), User.class);
                            String username = user.getUsername();
                            String password = user.getPassword();
                            try {
                                User validatedUser = model.validateLogin(username, password);
                                String userToJson = gson.toJson(validatedUser);
                                response = gson.toJson(new Message("LoggedIn", userToJson));
                            } catch (Exception e) {
                                response = gson.toJson(new Message(e.getMessage()));
                            }
                            break;
                        case "UpdateUser":
                            user = gson.fromJson(message.getData(), User.class);
                            try {
                                User updatedUser = model.updateUser(user);
                                String userToJson = gson.toJson(updatedUser);
                                response = gson.toJson(new Message("User updated", userToJson));
                            } catch (Exception e) {
                                response = gson.toJson(new Message(e.getMessage()));
                            }
                            break;
                        case "CreateTournament":
                            String TournamentJson = message.getData();
                            Tournament tournament = gson.fromJson(TournamentJson, Tournament.class);
                            int Id = model.createTournament(tournament);
                            Message IdToSend = new Message();
                            IdToSend.setData(Id + "");
                            response = gson.toJson(IdToSend);
                            break;
                        case "JoinTournament":
                            if (model.joinATournament(message.getData(), Integer.parseInt(message.getDataSlot2()))) {
                                response = gson.toJson(new Message("Success"));
                            } else {
                                response = gson.toJson(new Message("Fail"));
                            }
                            break;
                        case "GetMatches":
                            username = gson.fromJson(message.getData(), String.class);
                            System.out.println(username);
                            try {
                                ArrayList<Match> matches = model.getMatches(username);
                                String matchesToJson = gson.toJson(matches);
                                System.out.println(matchesToJson);
                                response = gson.toJson(new Message("Matches", matchesToJson));
                            } catch (Exception e) {
                                e.printStackTrace();
                                response = gson.toJson(new Message(e.getMessage()));
                            }
                            break;
                        case "GetMatch":
                            int matchId = Integer.parseInt(message.getData());
                            try {
                                Match match = model.getMatch(matchId);
                                String matchJson = gson.toJson(match);
                                response = gson.toJson(new Message("Match", matchJson));
                            } catch (Exception e) {
                                e.printStackTrace();
                                response = gson.toJson(new Message(e.getMessage()));
                            }
                            break;
                        case "GetMatchHistory":
                            username = gson.fromJson(message.getData(), String.class);
                            try {
                                ArrayList<Match> matchHistory = model.getMatchHistory(username);
                                String matchesToJson = gson.toJson(matchHistory);
                                response = gson.toJson(new Message("MatchHistory", matchesToJson));
                            } catch (Exception e) {
                                response = gson.toJson(new Message(e.getMessage()));
                            }
                            break;
                        case "UpdateOutcome":
                            username = message.getData();
                            String outcome = message.getDataSlot2();
                            matchId = Integer.parseInt(message.getDataSlot3());
                            model.updateOutcome(username, outcome, matchId);
                            response = gson.toJson(new Message(":D"));
                            break;
                        case "MoveHistory":
                            matchId = gson.fromJson(message.getData(), Integer.class);
                            try {
                                ArrayList<Move> moves = model.getMoves(matchId);
                                String movesToJson = gson.toJson(moves);
                                response = gson.toJson(new Message("HistoryOfMoves", movesToJson));
                                System.out.println(response);
                            } catch (Exception e) {
                                response = gson.toJson(new Message(e.getMessage()));
                            }
                            break;
                        case "TournamentHistory":
                            username = gson.fromJson(message.getData(), String.class);
                            String tournamentsToSend = gson.toJson(model.getAllTournamentsWhereAUserHasBeen(username));
                            response = gson.toJson(new Message("TournamentHistory", tournamentsToSend));
                            break;
                        default:
                            break;
                    }
                    System.out.println("Response " + response);

                } catch (RuntimeException e) {
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
