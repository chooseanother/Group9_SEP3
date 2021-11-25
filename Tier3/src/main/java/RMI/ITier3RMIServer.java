package RMI;/*
 * 12.09.2018 Original version
 */

import model.Challenge;
import model.Match;
import model.Move;
import model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


public interface ITier3RMIServer
        extends Remote {
    public boolean registerUser(User user) throws RemoteException;

    boolean validateChallenge(Challenge challenge) throws RemoteException;

    ArrayList<Challenge> loadChallenges() throws RemoteException;

    ArrayList<Challenge> loadChallenges(String username) throws RemoteException;

    boolean rejectChallenge(Challenge challenge) throws RemoteException;

    User validateLogin(String username, String password) throws RemoteException;

    boolean updateUser(User user) throws RemoteException;

    User getUser(String username) throws RemoteException;
    
    boolean UpdateMatchUserTurn(int matchID,String color) throws RemoteException;

    Match createMatch(int turnTime) throws RemoteException;

    boolean createParticipation(String username, String color, int matchId) throws RemoteException;

    boolean removeChallenge(Challenge challenge) throws RemoteException;

    public boolean MovePiece(int matchId, String piece, String color, String startPosition, String endPosition) throws RemoteException;

    public boolean UpgradePiece(int matchId, String piece, String color, String startPosition, String endPosition) throws RemoteException;

    ArrayList<Move> getMoves(int matchID) throws RemoteException;

    void updateOutcome(String player, String outcome, int matchId) throws RemoteException;

    String getParticipationColor(String player, int matchId) throws RemoteException;

    public static final String T3_SERVICE_NAME = "rmi://localhost/T3";
}
