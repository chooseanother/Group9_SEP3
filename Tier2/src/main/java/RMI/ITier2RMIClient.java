package RMI;

import model.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface ITier2RMIClient extends Remote {
    boolean registerUser(String username, String password, String email) throws RemoteException;

    boolean MovePiece(ChessPiece chessPiece, int matchId) throws RemoteException;

    boolean UpgradePiece(ChessPiece chessPiece, int matchId) throws RemoteException;

    boolean validateChallenge(Challenge challenge) throws RemoteException;

    ArrayList<Challenge> loadChallenges() throws RemoteException;

    ArrayList<Challenge> loadChallenges(String username) throws RemoteException;

    boolean rejectChallenge(Challenge challenge) throws RemoteException;

    User validateLogin(String username, String password) throws RemoteException;

    boolean UpdateMatchUserTurn(int matchID,String color) throws RemoteException;

    boolean updateUser(User user) throws RemoteException;

    User getUser(String username) throws RemoteException;

    ArrayList<Move> getMoves(int matchID) throws RemoteException;

    ArrayList<Match> getMatches(String username) throws RemoteException;

    ArrayList<Participant> getParticipants(int matchId) throws RemoteException;

    Match createMatch(int turnTime) throws RemoteException;

    boolean createParticipation(String username, String color, int matchId) throws RemoteException;

    boolean removeChallenge(Challenge challenge) throws RemoteException;

    void updateOutcome(String player, String outcome, int matchId) throws RemoteException;

    String getParticipationColor(String player, int matchId) throws RemoteException;

    boolean setMatchOutcome(int matchId, boolean finished) throws RemoteException;
}
