package RMI;

import model.ChessPiece;
import model.User;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class Tier2RMIClient extends UnicastRemoteObject implements ITier2RMIClient {
    private ITier3RMIServer tier3;

    public Tier2RMIClient() throws RemoteException {
        try {

            tier3 =(ITier3RMIServer) Naming.lookup(ITier3RMIServer.T3_SERVICE_NAME);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean registerUser(String username, String password, String email) throws RemoteException {

            try{
                return tier3.registerUser(new User(username, password, email));
            }catch (IllegalArgumentException e){
                return false;
                // actions to counter illegal data
            }

    }

    @Override public boolean MovePiece(ChessPiece piece, int matchId) throws RemoteException {
        try {
           return tier3.MovePiece( matchId, piece.getType(), piece.getColor(), piece.getOldPosition().getVerticalAxis()+":"+piece.getOldPosition().getHorizontalAxis()
                   , piece.getNewPosition().getVerticalAxis()+":"+piece.getNewPosition().getHorizontalAxis());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override public boolean UpgradePiece(ChessPiece chessPiece, int matchID){
        try {
            return tier3.UpgradePiece(matchID, chessPiece.getType(), chessPiece.getColor(), chessPiece.getOldPosition().getVerticalAxis()+":"+chessPiece.getOldPosition().getHorizontalAxis()
                    , chessPiece.getNewPosition().getVerticalAxis()+":"+chessPiece.getNewPosition().getHorizontalAxis());
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
