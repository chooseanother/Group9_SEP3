package RMI;

import model.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


public interface ITier2RMIClient extends Remote {
    boolean registerUser(String username, String password, String email) throws RemoteException;

    boolean movePiece(ChessPiece chessPiece, int matchId) throws RemoteException;

    boolean upgradePiece(ChessPiece chessPiece, int matchId) throws RemoteException;

    boolean validateChallenge(Challenge challenge) throws RemoteException;

    ArrayList<Challenge> loadChallenges() throws RemoteException;

    ArrayList<Challenge> loadChallenges(String username) throws RemoteException;

    boolean rejectChallenge(Challenge challenge) throws RemoteException;

    User validateLogin(String username, String password) throws RemoteException;

    boolean updateMatchUsersTurn(int matchID, String color) throws RemoteException;

    boolean updateUser(User user) throws RemoteException;

    User getUser(String username) throws RemoteException;

    int validateTournament(Tournament tournament) throws RemoteException;

    boolean joinATournament(String username, int tournamentID) throws RemoteException;

    Tournament getTournamentById(int id) throws RemoteException;

    ArrayList<TournamentParticipation> getTournamentParticipationByTournamentID(int id) throws RemoteException;

    ArrayList<Move> getMoves(int matchID) throws RemoteException;

    ArrayList<Match> getMatches(String username) throws RemoteException;

    ArrayList<Participant> getParticipants(int matchId) throws RemoteException;

    Match createMatch(int turnTime) throws RemoteException;

    boolean createParticipation(String username, String color, int matchId) throws RemoteException;

    boolean removeChallenge(Challenge challenge) throws RemoteException;

    Match createMatch(int turnTime, int tournamentID) throws RemoteException;

    void updateParticipantsPlacement(String username, int placement, int tournamentId) throws RemoteException;

    void updateTournamentNrOfParticipants(int ID, int newSize) throws RemoteException;

    void updateOutcome(String player, String outcome, int matchId) throws RemoteException;

    boolean setMatchOutcome(int matchId, boolean finished) throws RemoteException;

    Match getMatch(int matchId) throws RemoteException;

    ArrayList<Tournament> getAllTournamentsWhereAUserHasBeen(String username) throws RemoteException;

    ArrayList<TournamentParticipation> getTopPlayersInATournament(int tournamentID) throws RemoteException;

    public void setTournamentOutcome(int tournamentId, boolean finished) throws RemoteException;

    void incrementWinLossDraw(String username, String type) throws RemoteException;

}
