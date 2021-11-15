package RMI;/*
 * 12.09.2018 Original version
 */

import RMI.ITier3RMIServer;
import model.User;
import persistence.Persistence;
import persistence.PersistenceDB;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;


public class Tier3RMIServerController
        extends UnicastRemoteObject
        implements ITier3RMIServer {
    private Persistence persistence;

    public Tier3RMIServerController()
            throws RemoteException {
        try {
            startRegistry();
            startServer();
			System.out.println("Server started...");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        persistence = new PersistenceDB();
    }

    private void startRegistry() throws RemoteException {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            System.out.println("Registry started");
        } catch (ExportException e) {
            System.err.println("Server exception: " + e);
            e.printStackTrace();
        }
    }

    private void startServer() throws MalformedURLException, RemoteException {
        Naming.rebind(T3_SERVICE_NAME, this);
        System.out.println("Server ready");
    }

    @Override
    public boolean registerUser(User user) throws RemoteException {
        try {
            persistence.registerUser(user);
            System.out.println(user.getUserName() + " was created.");
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override public boolean MovePiece(int moveId, int matchId, String piece, String color, String startPosition, String endPosition){
        try {
            persistence.MovePiece(moveId, matchId, piece, color, startPosition, endPosition);
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }


}
