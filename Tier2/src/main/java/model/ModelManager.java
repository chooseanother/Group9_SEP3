package model;

import RabbitMQ.RabbitMQClient;
import RabbitMQ.RabbitMQClientController;
import RMI.ITier2RMIClient;
import RMI.Tier2RMIClient;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ModelManager implements Model{
    private ITier2RMIClient iTier2RMIClient;
    private RabbitMQClient rabbitMQClient;
    private ChessBoard chessBoard; //Should be match

    public ModelManager() throws RemoteException {
        iTier2RMIClient = new Tier2RMIClient();
        chessBoard = new ChessBoard();
        rabbitMQClient = new RabbitMQClientController(this);

        try{
            rabbitMQClient.initRPCQueue();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String registerUser(String username, String password, String email) {
        try {
            if (iTier2RMIClient.registerUser(username, password, email))
                return "Registered successfully";
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return "Failed registration";
    }

    @Override
    public ChessPiece MoveChessPiece(int firstLayer, int secondLayer) {
        try {
            ChessPiece toMove = chessBoard.MoveAttackChessPiece(firstLayer, secondLayer,iTier2RMIClient,1);
            if(toMove==null){
                System.out.println("Chess Piece was not moved, as it was not saved");
            }
               return toMove;

        } catch (RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ChessPiece UpgradeChessPiece(String upgradeSelected) {
       try {
            ChessPiece toUpgrade = chessBoard.UpgradeChessPiece(upgradeSelected, iTier2RMIClient,1);
            if(toUpgrade==null){
                System.out.println("Chess piece was not upgraded as it was not saved");
            }
            return toUpgrade;
        }catch (RemoteException e){
           e.printStackTrace();
           return null;
       }
    }

    @Override
    public ChessPiece[][] getChessBoard() {
        return chessBoard.getChessBoard();
    }

    @Override
    public String validateChallenge(Challenge challenge) {
        try {
            if (challenge.getChallenger().equals(challenge.getChallenged())){
                return "Can't challenge your self";
            }
            if (iTier2RMIClient.validateChallenge(challenge))
                return "Success";
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return "User doesn't exist or you already challenged that user.";
    }

    @Override public User validateLogin(String userName, String password)
    {
        try
        {
            User userFromDB = iTier2RMIClient.validateLogin(userName, password);
            if (userFromDB == null){
                throw new IllegalArgumentException("Wrong username or password");
            }
            return userFromDB;
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Challenge> loadChallenges() {
        try {
            return iTier2RMIClient.loadChallenges();
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Challenge> loadChallenges(String username) {
        try {
            return iTier2RMIClient.loadChallenges(username);
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean acceptChallenge(Challenge challenge) {
        try{
//            return iTier2RMIClient.acceptChallenge(challenge);
            if (iTier2RMIClient.acceptChallenge(challenge)){
//                iTier2RMIClient.createMatch(challenge.getChallenger(), challenge.getChallenged(), challenge.getTurnTime());
                return true;
            }
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean rejectChallenge(Challenge challenge) {
        try{
            return iTier2RMIClient.rejectChallenge(challenge);
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<ChessPiece> getRemovedChessPieces() {
        return chessBoard.getRemovedChessPieces();
    }
}
