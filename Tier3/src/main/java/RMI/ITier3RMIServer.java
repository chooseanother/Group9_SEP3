package RMI;/*
 * 12.09.2018 Original version
 */

import model.Challenge;
import model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


public interface ITier3RMIServer
	extends Remote
{
	public boolean registerUser(User user) throws RemoteException;
	boolean validateChallenge(Challenge challenge) throws RemoteException;
	ArrayList<Challenge> loadChallenges() throws RemoteException;
	ArrayList<Challenge> loadChallenges(String username) throws RemoteException;
	boolean acceptChallenge(Challenge challenge) throws RemoteException;
	boolean rejectChallenge(Challenge challenge) throws RemoteException;
//	void createMatch(String challenger, String challenged, int turnTime) throws RemoteException;


	public static final String T3_SERVICE_NAME = "rmi://localhost/T3";
}
