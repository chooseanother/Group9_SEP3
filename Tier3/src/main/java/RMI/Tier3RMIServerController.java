package RMI;/*
 * 12.09.2018 Original version
 */

import model.Challenge;
import model.Move;
import model.User;
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
            System.out.println(user.getUsername() + " was created.");
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }


    @Override public boolean MovePiece( int matchId, String piece, String color, String startPosition, String endPosition){
        try {
            persistence.MovePiece( matchId, piece, color, startPosition, endPosition);
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
    public boolean UpgradePiece(int matchId, String piece, String color, String startPosition, String endPosition) throws RemoteException {
        try {
            persistence.UpgradePiece( matchId,  piece,  color,  startPosition, endPosition);
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<Move> getMoves(int matchID) throws RemoteException {
        try {
            return persistence.getMoves(matchID);
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

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

    @Override public User validateLogin(String username, String password)
        throws RemoteException
    {
        try{
            User user = persistence.validateLogin(username, password);
            return user;
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override

    public boolean UpdateMatchUserTurn(int matchID, String color) throws RemoteException{
        try{
            persistence.UpdateMatchUserTurn(matchID,color);
            return true;
        }catch (SQLException e){
            return false;
        }
    }
    
    @Override
    public boolean updateUser(User user) throws RemoteException {
        try{
            persistence.updateUser(user);
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User getUser(String username) throws RemoteException {
        try{
            User user = persistence.getUser(username);
            return user;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }


    //    @Override
//    public void createMatch(String challenger, String challenged, int turnTime) throws RemoteException {
//
//    }

}
