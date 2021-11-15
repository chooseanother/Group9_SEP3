package model;

import RabbitMQ.RabbitMQClient;
import RabbitMQ.RabbitMQClientController;
import RMI.ITier2RMIClient;
import RMI.Tier2RMIClient;


import java.rmi.RemoteException;

public class ModelManager implements Model{
    private ITier2RMIClient iTier2RMIClient;
    private RabbitMQClient rabbitMQClient;
    private ChessBoard chessBoard; //Should be match
    private int moveId;

    public ModelManager() throws RemoteException {
        iTier2RMIClient = new Tier2RMIClient();
        chessBoard = new ChessBoard();
        rabbitMQClient = new RabbitMQClientController(this);
        moveId = 0;

        try{
            rabbitMQClient.initRPCQueue();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String registerUser(String username, String password, String email) {
        try {
            if (iTier2RMIClient.registerUser(username, password, email))
                return "Registered successfully";
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return "Failed registration";
    }

    @Override
    public ChessPiece MoveChessPiece(int firstLayer, int secondLayer) {
        try {
            moveId++;
            ChessPiece toMove = chessBoard.HandleClick(firstLayer, secondLayer);
            if(iTier2RMIClient.MovePiece(toMove, moveId, 0 )){
               return toMove;
            }

        } catch (RemoteException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ChessPiece UpgradeChessPiece(String upgradeSelected) {
        try {
            ChessPiece toUpgrade = chessBoard.UpgradeChessPiece(upgradeSelected);
            if (iTier2RMIClient.UpgradePiece(upgradeSelected)) {
                return toUpgrade;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
       return null;
    }

    @Override
    public ChessPiece[][] getChessBoard() {
        return chessBoard.getChessBoard();
    }
}
