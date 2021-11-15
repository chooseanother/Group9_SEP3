package RMI;

import model.ChessPiece;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ITier2RMIClient extends Remote {
    boolean registerUser(String username, String password, String email) throws RemoteException;

    public boolean MovePiece(ChessPiece chessPiece, int moveId, int matchId) throws RemoteException;
}
