package RMI;/*
 * 12.09.2018 Original version
 */

import model.Challenge;
import model.Tournament;
import model.TournamentParticipation;
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

    boolean acceptChallenge(Challenge challenge) throws RemoteException;

    boolean rejectChallenge(Challenge challenge) throws RemoteException;

    User validateLogin(String username, String password) throws RemoteException;

    boolean updateUser(User user) throws RemoteException;

    User getUser(String username) throws RemoteException;
    
    boolean UpdateMatchUserTurn(int matchID,String color) throws RemoteException;

//	void createMatch(String challenger, String challenged, int turnTime) throws RemoteException;


    public boolean MovePiece(int matchId, String piece, String color, String startPosition, String endPosition) throws RemoteException;

    public boolean UpgradePiece(int matchId, String piece, String color, String startPosition, String endPosition) throws RemoteException;

    public int validateTournament(Tournament tournament) throws RemoteException;

    public boolean joinATournament(String username, int tournamentID, int placement) throws RemoteException;

    public Tournament GetTournamentById(int id);

    public ArrayList<TournamentParticipation> getTournamentParticipationByTournamentID(int id);

    public static final String T3_SERVICE_NAME = "rmi://localhost/T3";
}
