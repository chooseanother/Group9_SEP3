package RMI;/*
 * 12.09.2018 Original version
 */

import model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ITier3RMIServer
	extends Remote
{
	public boolean registerUser(User user) throws RemoteException;

	public static final String T3_SERVICE_NAME = "rmi://localhost/T3";
}
