package RMI;
import model.Challenge;
import model.Tournament;
import model.TournamentParticipation;
import model.Match;
import model.Move;
import model.User;
import model.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


public interface ITier3RMIServer
        extends Remote {
    boolean registerUser(User user) throws RemoteException;

    boolean validateChallenge(Challenge challenge) throws RemoteException;

    ArrayList<Challenge> loadChallenges() throws RemoteException;

    ArrayList<Challenge> loadChallenges(String username) throws RemoteException;

    boolean rejectChallenge(Challenge challenge) throws RemoteException;

    User validateLogin(String username, String password) throws RemoteException;

    boolean updateUser(User user) throws RemoteException;

    User getUser(String username) throws RemoteException;
    
    boolean updateMatchUsersTurn(int matchID, String color) throws RemoteException;

    Match createMatch(int turnTime) throws RemoteException;

    Match createMatch(int turnTime, int tournamentID) throws RemoteException;

    boolean createParticipation(String username, String color, int matchId) throws RemoteException;

    boolean removeChallenge(Challenge challenge) throws RemoteException;

    boolean movePiece(int matchId, String piece, String color, String startPosition, String endPosition) throws RemoteException;

    boolean upgradePiece(int matchId, String piece, String color, String startPosition, String endPosition) throws RemoteException;

    ArrayList<Move> getMoves(int matchID) throws RemoteException;

    int validateTournament(Tournament tournament) throws RemoteException;

    boolean joinATournament(String username, int tournamentID) throws RemoteException;

    Tournament getTournamentById(int id) throws RemoteException;

    ArrayList<TournamentParticipation> getTournamentParticipationByTournamentID(int id) throws RemoteException;

    void updateTournamentNrOfParticipants(int ID, int newSize) throws RemoteException;

    void updateParticipantsPlacement(String username, int placement, int tournamentId) throws RemoteException;

    ArrayList<Match> getMatches(String username) throws RemoteException;

    ArrayList<Participant> getParticipants(int matchId) throws RemoteException;

    void updateOutcome(String player, String outcome, int matchId) throws RemoteException;

    boolean setMatchOutcome(int matchId, boolean finished) throws RemoteException;

    Match getMatch(int matchId) throws RemoteException;

    void incrementWinLossDraw(String username, String type) throws RemoteException;

    ArrayList<Tournament> getAllTournamentsWhereAUserHasBeen(String username) throws RemoteException;

    ArrayList<TournamentParticipation> getTopPlayersInATournament(int tournamentID) throws RemoteException;

    public void setTournamentOutcome(int tournamentId, boolean finished) throws RemoteException;

    static final String T3_SERVICE_NAME = "rmi://localhost/T3";
}
