package RMI;

import model.ChessPiece;
import model.Challenge;
import model.User;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Tier2RMIClient extends UnicastRemoteObject implements ITier2RMIClient {
    private ITier3RMIServer tier3;

    public Tier2RMIClient() throws RemoteException {
        try {

            tier3 =(ITier3RMIServer) Naming.lookup(ITier3RMIServer.T3_SERVICE_NAME);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean registerUser(String username, String password, String email) throws RemoteException {

            try{
                return tier3.registerUser(new User(username, password, email));
            }catch (IllegalArgumentException e){
                return false;
                // actions to counter illegal data
            }

    }

    @Override public boolean MovePiece(ChessPiece piece, int matchId) throws RemoteException {
        try {
           return tier3.MovePiece( matchId, piece.getType(), piece.getColor(), piece.getOldPosition().getVerticalAxis()+":"+piece.getOldPosition().getHorizontalAxis()
                   , piece.getNewPosition().getVerticalAxis()+":"+piece.getNewPosition().getHorizontalAxis());
        } catch (Exception e) {
          e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean validateChallenge(Challenge challenge) throws RemoteException {
        try{
            return tier3.validateChallenge(challenge);
        }catch (IllegalArgumentException e){
            return false;
            // actions to counter illegal data
        }
    }

    @Override
    public ArrayList<Challenge> loadChallenges() throws RemoteException {
        try {
            ArrayList<Challenge> challenges = (ArrayList<Challenge>) tier3.loadChallenges();
            return challenges;
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Challenge> loadChallenges(String username) throws RemoteException {
        try {
            ArrayList<Challenge> challenges = (ArrayList<Challenge>) tier3.loadChallenges(username);
            return challenges;
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean acceptChallenge(Challenge challenge) throws RemoteException {
        try {
            return tier3.acceptChallenge(challenge);
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            return false;
        }
    }


    @Override public boolean UpgradePiece(ChessPiece chessPiece, int matchID){
        try {
            return tier3.UpgradePiece(matchID, chessPiece.getType(), chessPiece.getColor(), chessPiece.getOldPosition().getVerticalAxis()+":"+chessPiece.getOldPosition().getHorizontalAxis()
                    , chessPiece.getNewPosition().getVerticalAxis()+":"+chessPiece.getNewPosition().getHorizontalAxis());
        } catch (Exception e){
          e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean rejectChallenge(Challenge challenge) throws RemoteException {
        try {
            return tier3.rejectChallenge(challenge);
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override public User validateLogin(String username, String password)
        throws RemoteException
    {
        try{
            return tier3.validateLogin(username, password);
        }catch (IllegalArgumentException e){
            return null;
        }
    }

    @Override
    public boolean UpdateMatchUserTurn(int matchID, String color) throws RemoteException {
        try{
            return tier3.UpdateMatchUserTurn(matchID,color);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //    @Override
//    public void createMatch(String challenger, String challenged, int turnTime) throws RemoteException {
//        try {
//            tier3.createMatch(challenger, challenged, turnTime);
//        } catch (IllegalArgumentException e){
//            e.printStackTrace();
//        }
//    }
}
