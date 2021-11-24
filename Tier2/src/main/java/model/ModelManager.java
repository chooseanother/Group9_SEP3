package model;

import RabbitMQ.RabbitMQClient;
import RabbitMQ.RabbitMQClientController;
import RMI.ITier2RMIClient;
import RMI.Tier2RMIClient;
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
    public ChessPiece MoveChessPiece(int firstLayer, int secondLayer) {
        try {
            ChessPiece toMove = getChessBoard().MoveAttackChessPiece(firstLayer, secondLayer, iTier2RMIClient, 1);
            System.out.println("Test in model manager toMove: "+toMove+" rmiclient: "+iTier2RMIClient);
            if (toMove == null) {
                System.out.println("Chess Piece was not moved, as it was not saved");
            }
            return toMove;

        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ChessPiece UpgradeChessPiece(String upgradeSelected) {
        try {
            ChessPiece toUpgrade = getChessBoard().UpgradeChessPiece(upgradeSelected, iTier2RMIClient, 1);
            if (toUpgrade == null) {
                System.out.println("Chess piece was not upgraded as it was not saved");
            }
            return toUpgrade;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ChessBoard getChessBoard() {
        ChessBoard chessBoard = new ChessBoard();
        try{
            ArrayList<Move> moves = iTier2RMIClient.getMoves(1);
            if (moves.size() > 0) {
                for (Move m : moves) {
                    String[] start = m.getStartPosition().split(":");
                    String[] end = m.getEndPosition().split(":");
                    if (m.getStartPosition().equals(m.getEndPosition())) {
                        chessBoard.MoveAttackChessPiece(Integer.parseInt(start[0]), Integer.parseInt(start[1]), null, 1);
                        chessBoard.UpgradeChessPiece(m.getPiece(), null, 1);
                    } else {
                        chessBoard.MoveAttackChessPiece(Integer.parseInt(start[0]), Integer.parseInt(start[1]), null, 1);
                        chessBoard.MoveAttackChessPiece(Integer.parseInt(end[0]), Integer.parseInt(end[1]), null, 1);
                    }
                }
            }
        } catch (RemoteException e){
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
//            return iTier2RMIClient.acceptChallenge(challenge);
            if (iTier2RMIClient.acceptChallenge(challenge)) {
//                iTier2RMIClient.createMatch(challenge.getChallenger(), challenge.getChallenged(), challenge.getTurnTime());
                return true;
            }
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
}
