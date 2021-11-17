package RMI;/*
 * 12.09.2018 Original version
 */

import RMI.ITier3RMIServer;
import model.Challenge;
import model.User;
import persistence.ChallengePersistence;
import persistence.Persistence;
import persistence.PersistenceDB;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Tier3RMIServerController
        extends UnicastRemoteObject
        implements ITier3RMIServer {
    private Persistence persistence;

    public Tier3RMIServerController()
            throws RemoteException {
        try {
            startRegistry();
            startServer();
			System.out.println("Server started...");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        persistence = new PersistenceDB();
    }

    private void startRegistry() throws RemoteException {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            System.out.println("Registry started");
        } catch (ExportException e) {
            System.err.println("Server exception: " + e);
            e.printStackTrace();
        }
    }

    private void startServer() throws MalformedURLException, RemoteException {
        Naming.rebind(T3_SERVICE_NAME, this);
        System.out.println("Server ready");
    }

    @Override
    public boolean registerUser(User user) throws RemoteException {
        try {
            persistence.registerUser(user);
            System.out.println(user.getUserName() + " was created.");
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean validateChallenge(Challenge challenge) throws RemoteException {
        try {
            persistence.createChallenge(challenge);
            System.out.println(challenge + " was created.");
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<Challenge> loadChallenges() throws RemoteException {
        try {
            ArrayList<Challenge> challenges = persistence.loadChallenges();
            return challenges;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Challenge> loadChallenges(String username) throws RemoteException {
        try {
            ArrayList<Challenge> challenges = persistence.loadChallenges(username);
            return challenges;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean acceptChallenge(Challenge challenge) throws RemoteException {
        try {
            int matchId = persistence.createMatch(challenge.getTurnTime(), "Friendly");
            persistence.createMatchParticipation(challenge.getChallenger(),"White",matchId);
            persistence.createMatchParticipation(challenge.getChallenged(),"Black",matchId);
            return persistence.deleteChallenge(challenge);
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean rejectChallenge(Challenge challenge) throws RemoteException {
        try {
            return persistence.deleteChallenge(challenge);
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

//    @Override
//    public void createMatch(String challenger, String challenged, int turnTime) throws RemoteException {
//
//    }
}
