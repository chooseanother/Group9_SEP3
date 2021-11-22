package RMI;

import model.ChessPiece;
import model.Challenge;
import model.Move;
import model.User;

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

    boolean acceptChallenge(Challenge challenge) throws RemoteException;

    boolean rejectChallenge(Challenge challenge) throws RemoteException;

    User validateLogin(String username, String password) throws RemoteException;

    boolean UpdateMatchUserTurn(int matchID,String color) throws RemoteException;

    boolean updateUser(User user) throws RemoteException;

    User getUser(String username) throws RemoteException;

    ArrayList<Move> getMoves(int matchID) throws RemoteException;

//    void createMatch(String challenger, String challenged, int turnTime) throws RemoteException;
}
