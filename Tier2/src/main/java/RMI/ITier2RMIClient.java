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

    public int validateTournament(Tournament tournament) throws RemoteException;

    public boolean joinATournament(String username, int tournamentID, int placement) throws RemoteException;

    public Tournament GetTournamentById(int id) throws RemoteException;

    public ArrayList<TournamentParticipation> getTournamentParticipationByTournamentID(int id) throws RemoteException;

//    void createMatch(String challenger, String challenged, int turnTime) throws RemoteException;
    ArrayList<Move> getMoves(int matchID) throws RemoteException;

    Match createMatch(int turnTime) throws RemoteException;

    boolean createParticipation(String username, String color, int matchId) throws RemoteException;

    boolean removeChallenge(Challenge challenge) throws RemoteException;


    ArrayList<Move> getMoves(int matchID) throws RemoteException;

    Match createMatch(int turnTime) throws RemoteException;

    public Match createMatch(int turnTime, int tournamentID) throws RemoteException;

    boolean createParticipation(String username, String color, int matchId) throws RemoteException;

    boolean removeChallenge(Challenge challenge) throws RemoteException;

    public void UpdateTournamentNrOfParticipants(int ID, int newSize) throws RemoteException;


}
