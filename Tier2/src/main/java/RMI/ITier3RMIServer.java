package RMI;/*
 * 12.09.2018 Original version
 */

import model.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


public interface ITier3RMIServer
	extends Remote
{

	boolean UpdateMatchUserTurn(int matchID,String color) throws RemoteException;

    public boolean registerUser(User user) throws RemoteException;

    boolean validateChallenge(Challenge challenge) throws RemoteException;

    ArrayList<Challenge> loadChallenges() throws RemoteException;

    ArrayList<Challenge> loadChallenges(String username) throws RemoteException;

    boolean rejectChallenge(Challenge challenge) throws RemoteException;

    User validateLogin(String username, String password) throws RemoteException;

    boolean updateUser(User user) throws RemoteException;

    User getUser(String username) throws RemoteException;

    public boolean MovePiece(int matchId, String piece, String color, String startPosition, String endPosition) throws RemoteException;

    public boolean UpgradePiece(int matchId, String piece, String color, String startPosition, String endPosition) throws RemoteException;

    ArrayList<Move> getMoves(int matchID) throws RemoteException;

    ArrayList<Match> getMatches(String username) throws RemoteException;

    ArrayList<Participant> getParticipants(int matchId) throws RemoteException;

    Match createMatch(int turnTime) throws RemoteException;

    public Match createMatch(int turnTime, int tournamentID) throws RemoteException;

    public void UpdateTournamentNrOfParticipants(int ID, int newSize) throws RemoteException;

    boolean createParticipation(String username, String color, int matchId) throws RemoteException;

    boolean removeChallenge(Challenge challenge) throws RemoteException;

    void updateOutcome(String player, String outcome, int matchId) throws RemoteException;

    String getParticipationColor(String player, int matchId) throws RemoteException;

    boolean setMatchOutcome(int matchId, boolean finished) throws RemoteException;

    Match getMatch(int matchId) throws RemoteException;

    public int validateTournament(Tournament tournament) throws RemoteException;

    public boolean joinATournament(String username, int tournamentID, int placement) throws RemoteException;

    public Tournament GetTournamentById(int id) throws RemoteException;

    public ArrayList<TournamentParticipation> getTournamentParticipationByTournamentID(int id) throws RemoteException;

    public void UpdateParticipantsPlacement(String username, int placement, int tournamentId) throws RemoteException;

    public int getNrofOriginalParticipants(int tournamentID) throws RemoteException;

    void incrementWinLossDraw(String username, String type) throws RemoteException;


    public static final String T3_SERVICE_NAME = "rmi://localhost/T3";
}
