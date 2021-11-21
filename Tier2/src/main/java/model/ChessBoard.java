package model;


import RMI.ITier2RMIClient;

import java.rmi.RemoteException;

/**
 * @author Nick/Rokas
 * @version 1.0
 */

public class ChessBoard {
    private ChessPiece[][] chessPieces;
//    private ChessPiece selected;

    /**
     * Creating a chess board and setting the default chess pieces locations
     */
    public ChessBoard() {
        chessPieces = new ChessPiece[8][8];
//        selected = null;

        //black
        String black = "Black";
        chessPieces[0][0] = new ChessPiece("Rook", black, new Position(0, 0));
        chessPieces[0][1] = new ChessPiece("Knight", black, new Position(0, 1));
        chessPieces[0][2] = new ChessPiece("Bishop", black, new Position(0, 2));
        chessPieces[0][3] = new ChessPiece("Queen", black, new Position(0, 3));
        chessPieces[0][4] = new ChessPiece("King", black, new Position(0, 4));
        chessPieces[0][5] = new ChessPiece("Bishop", black, new Position(0, 5));
        chessPieces[0][6] = new ChessPiece("Knight", black, new Position(0, 6));
        chessPieces[0][7] = new ChessPiece("Rook", black, new Position(0, 7));

        for (int i = 0; i < 8; i++) {
            chessPieces[1][i] = new ChessPiece("Pawn", black, new Position(1, i));
        }

        //white
        String white = "White";
        chessPieces[7][0] = new ChessPiece("Rook", white, new Position(7, 0));
        chessPieces[7][1] = new ChessPiece("Knight", white, new Position(7, 1));
        chessPieces[7][2] = new ChessPiece("Bishop", white, new Position(7, 2));
        chessPieces[7][3] = new ChessPiece("Queen", white, new Position(7, 3));
        chessPieces[7][4] = new ChessPiece("King", white, new Position(7, 4));
        chessPieces[7][5] = new ChessPiece("Bishop", white, new Position(7, 5));
        chessPieces[7][6] = new ChessPiece("Knight", white, new Position(7, 6));
        chessPieces[7][7] = new ChessPiece("Rook", white, new Position(7, 7));

        for (int i = 0; i < 8; i++) {
            chessPieces[6][i] = chessPieces[0][7] = new ChessPiece("Pawn", white, new Position(6, i));
            ;
        }

    }

    /**
     * A method to handle chess piece clicks. It can select, move, attack, deselect a piece
     *
     * @param firstLayer  Vertical layer
     * @param secondLayer Horizontal layer
     */
    public ChessPiece MoveAttackChessPiece(int firstLayer, int secondLayer, ITier2RMIClient iTier2RMIClient, int matchID)throws RemoteException {
        ChessPiece selected = null;
        String buildString = "";
        for (int i = 0; i < chessPieces.length; i++) {

            for (int j = 0; j < chessPieces[i].length; j++) {

                if (chessPieces[i][j] != null && chessPieces[i][j].getSelected() && selected == null) {
                    selected = chessPieces[i][j].copy();
                    if (selected.getNewPosition() == null) {
                        selected.setOldPosition(new Position(i, j));
                        selected.setNewPosition(new Position(firstLayer,secondLayer));
                    } else {
                        selected.setOldPosition(chessPieces[i][j].getNewPosition());
                        selected.setNewPosition(new Position(firstLayer,secondLayer));
                    }

                }
            }
        }
        if (chessPieces[firstLayer][secondLayer] != null) {
            chessPieces[firstLayer][secondLayer].setSelected(true);
        }
       if(selected!=null && selected.getOldPosition().getVerticalAxis() == firstLayer && selected.getOldPosition().getHorizontalAxis() == secondLayer){
            chessPieces[firstLayer][secondLayer].setSelected(false);
            selected=null;
        }
        if (selected != null) {
            selected.setNewPosition(new Position(firstLayer, secondLayer));
                String turnColor= "";
                if(selected.getColor().equals("Black")){
                    turnColor = "White";
                }else{
                    turnColor="Black";
                }
                if (iTier2RMIClient.MovePiece(selected, matchID) && iTier2RMIClient.UpdateMatchUserTurn(matchID,turnColor)) {
                    chessPieces[firstLayer][secondLayer] = selected.copy();
                    chessPieces[firstLayer][secondLayer].setNewPosition(new Position(firstLayer, secondLayer));
                    if (selected.getOldPosition().getVerticalAxis() != chessPieces[firstLayer][secondLayer].getNewPosition().getVerticalAxis()
                            || selected.getOldPosition().getHorizontalAxis() != chessPieces[firstLayer][secondLayer].getNewPosition().getHorizontalAxis()) {
                        chessPieces[selected.getOldPosition().getVerticalAxis()][selected.getOldPosition().getHorizontalAxis()] = null;
                    }

                    for (int i = 0; i < 8; i++) {

                        for (int j = 0; j < 8; j++) {

                            if (chessPieces[i][j] != null) {
                                chessPieces[i][j].setSelected(false);
                            }
                        }
                    }
                    return chessPieces[firstLayer][secondLayer];
                }
        }
        for (int i = 0; i < chessPieces.length; i++) {
            for (int j = 0; j < chessPieces[i].length; j++) {
                if (chessPieces[i][j] != null) {
                    buildString += "'" + chessPieces[i][j].getType() + "'";
                } else {
                    buildString += "''";
                }
            }
            buildString += "\n";
        }
        System.out.println(buildString);
        return null;
    }

    /**
     * A method to upgrade a chess piece
     *
     * @param UpgradeSelected the type of upgrade
     */
    public ChessPiece UpgradeChessPiece(String UpgradeSelected,ITier2RMIClient iTier2RMIClient,int matchID)throws RemoteException {

            for (int i = 0; i < chessPieces.length; i++) {

                for (int j = 0; j < chessPieces[i].length; j++) {

                    if (chessPieces[i][j] != null && chessPieces[i][j].getSelected()) {
                        ChessPiece selected = chessPieces[i][j].copy();
                        selected.setType(UpgradeSelected);
                        selected.setSelected(false);
                        selected.setOldPosition(new Position(i,j));
                        selected.setNewPosition(new Position(i,j));
                        if(iTier2RMIClient.UpgradePiece(selected,matchID)) {
                        chessPieces[i][j] = selected.copy();
                        return chessPieces[i][j];
                    }
                }
            }
        }
        return null;
    }

    public ChessPiece[][] getChessBoard() {
        return chessPieces;
    }
    /**
     * Returns the selected chess piece
     * @return piece
     */
//    public ChessPiece getSelected(){
//        return selected;
//    }
}
