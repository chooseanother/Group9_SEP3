package RMI;

import model.Challenge;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface ITier2RMIClient extends Remote {
    boolean registerUser(String username, String password, String email) throws RemoteException;
    boolean validateChallenge(Challenge challenge) throws RemoteException;
    ArrayList<Challenge> loadChallenges() throws RemoteException;
    ArrayList<Challenge> loadChallenges(String username) throws RemoteException;
    boolean acceptChallenge(Challenge challenge) throws RemoteException;
    boolean rejectChallenge(Challenge challenge) throws RemoteException;
//    void createMatch(String challenger, String challenged, int turnTime) throws RemoteException;
}