package model;

import RMI.ITier2RMIClient;
import RMI.Tier2RMIClient;
import RabbitMQ.RabbitMQClient;
import RabbitMQ.RabbitMQClientController;
import com.google.gson.Gson;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ModelManager implements Model {
    private ITier2RMIClient iTier2RMIClient;
    private RabbitMQClient rabbitMQClient;
    private ChessBoard chessBoard; //Should be match

    public ModelManager() throws RemoteException {
        iTier2RMIClient = new Tier2RMIClient();
//        chessBoard = new ChessBoard();
        rabbitMQClient = new RabbitMQClientController(this);

        // for testing only
//        ArrayList<Move> moves = iTier2RMIClient.getMoves(1);

//        if (moves.size() > 0) {
//            for (Move m : moves) {
////            System.out.println(new Gson().toJson(m));
//                String[] start = m.getStartPosition().split(":");
//                String[] end = m.getEndPosition().split(":");
//                if (m.getStartPosition().equals(m.getEndPosition())) {
//                    chessBoard.MoveAttackChessPiece(Integer.parseInt(start[0]), Integer.parseInt(start[1]), null, 1);
//                    chessBoard.UpgradeChessPiece(m.getPiece(), null, 1);
//                } else {
//                    chessBoard.MoveAttackChessPiece(Integer.parseInt(start[0]), Integer.parseInt(start[1]), null, 1);
//                    chessBoard.MoveAttackChessPiece(Integer.parseInt(end[0]), Integer.parseInt(end[1]), null, 1);
//                }
//            }
//        }


        try {
            rabbitMQClient.initRPCQueue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String registerUser(String username, String password, String email) {
        try {
            if (iTier2RMIClient.registerUser(username, password, email))
                return "Registered successfully";
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return "Failed registration";
    }

    @Override
    public ChessPiece MoveChessPiece(ChessPiece selected) {
        try {
            ChessPiece toMove = getChessBoard().MoveAttackChessPiece(selected, iTier2RMIClient, 1);
            return toMove;

        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ChessPiece UpgradeChessPiece(String upgradeSelected,ChessPiece toUpgrade) {
        try {
            ChessPiece upgraded = getChessBoard().UpgradeChessPiece(upgradeSelected, toUpgrade, iTier2RMIClient, 1);
            if (upgraded == null) {
                System.out.println("Chess piece was not upgraded as it was not saved");
            }
            return upgraded;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ChessBoard getChessBoard() {
        ChessBoard chessBoard = new ChessBoard();
        try {
            ArrayList<Move> moves = iTier2RMIClient.getMoves(1);
            if (moves.size() > 0) {
                for (Move m : moves) {
                    String[] start = m.getStartPosition().split(":");
                    String[] end = m.getEndPosition().split(":");
                    Position oldPosition = new Position(Integer.parseInt(start[0]), Integer.parseInt(start[1]));
                    Position newPosition = new Position(Integer.parseInt(end[0]), Integer.parseInt(end[1]));
                    ChessPiece toMove = new ChessPiece(m.getPiece(), m.getColor(), oldPosition, newPosition);
                    if (m.getStartPosition().equals(m.getEndPosition())) {
                        chessBoard.MoveAttackChessPiece(toMove, null, 1);
                        chessBoard.UpgradeChessPiece(m.getPiece(),toMove, null, 1);

                    } else {
                        chessBoard.MoveAttackChessPiece(toMove, null, 1);
                    }
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return chessBoard;
    }

    @Override
    public String validateChallenge(Challenge challenge) {
        try {
            if (challenge.getChallenger().equals(challenge.getChallenged())) {
                return "Can't challenge your self";
            }
            if (iTier2RMIClient.validateChallenge(challenge))
                return "Success";
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return "User doesn't exist or you already challenged that user.";
    }

    @Override
    public User validateLogin(String userName, String password) {
        try {
            User userFromDB = iTier2RMIClient.validateLogin(userName, password);
            if (userFromDB == null) {
                throw new IllegalArgumentException("Wrong username or password");
            }
            return userFromDB;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User updateUser(User user) {
        try {
            if (iTier2RMIClient.updateUser(user)) {
                User userFromDB = iTier2RMIClient.getUser(user.getUsername());
                return userFromDB;
            }
            throw new IllegalArgumentException("User details were not updated successfully. Please try different password and/or email");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Challenge> loadChallenges() {
        try {
            return iTier2RMIClient.loadChallenges();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Challenge> loadChallenges(String username) {
        try {
            return iTier2RMIClient.loadChallenges(username);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean acceptChallenge(Challenge challenge) {
        try {
            Match match = iTier2RMIClient.createMatch(challenge.getTurnTime());
            iTier2RMIClient.createParticipation(challenge.getChallenger(),"White",match.getMatchID());
            iTier2RMIClient.createParticipation(challenge.getChallenged(),"Black",match.getMatchID());
            iTier2RMIClient.removeChallenge(challenge);
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean rejectChallenge(Challenge challenge) {
        try {
            return iTier2RMIClient.rejectChallenge(challenge);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<ChessPiece> getRemovedChessPieces() {
        return getChessBoard().getRemovedChessPieces();
    }

    @Override
    public int getMatchScores(boolean Black) {
        if (Black){
            return getChessBoard().GetScore("Black");
        } else {
            return getChessBoard().GetScore("White");
        }
    }

    @Override public ArrayList<Match> getMatches(String username)
    {
        try{
            ArrayList<Match> matches = iTier2RMIClient.getMatches(username);
            System.out.println(new Gson().toJson(matches));
            matches.removeIf(Match::getFinished);
            System.out.println(new Gson().toJson(matches));
            for (Match m : matches){
                ArrayList<Participant> participants = iTier2RMIClient.getParticipants(m.getMatchID());
                for(Participant p: participants){
                    if(p.getColor().equals("Black")){
                        m.setBlackPlayer(p);
                    }
                    else{
                        m.setWhitePlayer(p);
                    }
                }
            }
            return matches;

        }catch (RemoteException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override public ArrayList<Match> getMatchHistory(String username)
    {
        try{
            ArrayList<Match> matches = iTier2RMIClient.getMatches(username);
            matches.removeIf(m -> !m.getFinished());
            for (Match m : matches){
                ArrayList<Participant> participants = iTier2RMIClient.getParticipants(m.getMatchID());
                for(Participant p: participants){
                    if(p.getColor().equals("Black")){
                        m.setBlackPlayer(p);
                    }
                    else{
                        m.setWhitePlayer(p);
                    }
                }
            }
            return matches;

        }catch (RemoteException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateOutcome(String player, String outcome, int matchId) {
        try{
            iTier2RMIClient.updateOutcome(player, outcome, matchId);

            ArrayList<Participant> participants = iTier2RMIClient.getParticipants(matchId);

            Participant player1 = participants.get(0);
            Participant player2 = participants.get(1);

            switch (player1.getOutcome()){
                case "Draw":
                    if (player2.getOutcome().equals("Draw")){
                        // set match to finished
                        iTier2RMIClient.setMatchOutcome(matchId,true);
                        // add 1 to drawn games for player 1 and player2
                        // add 1 to total games played for player1 and player2
                    }
                    break;
                case "Win":
                    if (player2.getOutcome().equals("Loss")){
                        // set match to finished
                        iTier2RMIClient.setMatchOutcome(matchId,true);
                        // award win to player1
                        // give loss to player2
                        // update total games played for player1 and player2
                    }
                    break;
                case "Loss":
                    if (player2.getOutcome().equals("Win")){
                        // set match to finished
                        iTier2RMIClient.setMatchOutcome(matchId,true);
                        // award win to player2
                        // give loss to player1
                        // update total games played for player1 and player2
                    }
                    break;
                default:

            }

            // Check if both match outcome for both players is market
            // do stuff based on mark from both players
        }catch (RemoteException e){
            e.printStackTrace();
        }
    }

    @Override
    public String getParticipationColor(String player, int matchId) {
        try{
            return iTier2RMIClient.getParticipationColor(player, matchId);
        }catch (RemoteException e){
            e.printStackTrace();
            return "";
        }
    }
}
