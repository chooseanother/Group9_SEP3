package RMI;

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
                return tier3.registerUser(new User(username, password, email, 0, 0, 0, 0, 0));
            }catch (IllegalArgumentException e){
                return false;
                // actions to counter illegal data
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

    //    @Override
//    public void createMatch(String challenger, String challenged, int turnTime) throws RemoteException {
//        try {
//            tier3.createMatch(challenger, challenged, turnTime);
//        } catch (IllegalArgumentException e){
//            e.printStackTrace();
//        }
//    }
}
