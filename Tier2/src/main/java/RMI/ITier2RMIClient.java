package RMI;

import model.ChessPiece;
import model.Challenge;
import model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface ITier2RMIClient extends Remote {
    boolean registerUser(String username, String password, String email) throws RemoteException;

    public boolean MovePiece(ChessPiece chessPiece, int matchId) throws RemoteException;

  public boolean UpgradePiece(ChessPiece chessPiece,int matchId) throws RemoteException;
  
  boolean validateChallenge(Challenge challenge) throws RemoteException;
    ArrayList<Challenge> loadChallenges() throws RemoteException;
    ArrayList<Challenge> loadChallenges(String username) throws RemoteException;
    boolean acceptChallenge(Challenge challenge) throws RemoteException;
    boolean rejectChallenge(Challenge challenge) throws RemoteException;
    User validateLogin(String username, String password) throws RemoteException;
//    void createMatch(String challenger, String challenged, int turnTime) throws RemoteException;
}
