package model;


import RMI.ITier2RMIClient;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * @author Nick/Rokas
 * @version 1.0
 */

public class ChessBoard {
    private ChessPiece[][] chessPieces;
    private ArrayList<ChessPiece> RemovedChessPieces;
    private int BlackScore;
    private int WhiteScore;
    private String turnColor;

    /**
     * Creating a chess board and setting the default chess pieces locations
     */
    public ChessBoard() {
        chessPieces = new ChessPiece[8][8];
        RemovedChessPieces = new ArrayList<>();
        BlackScore = 0;
        WhiteScore = 0;
        turnColor = "White";
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
            chessPieces[6][i] = new ChessPiece("Pawn", white, new Position(6, i));
            ;
        }

    }

    /**
     * A method to handle chess piece clicks. It can select, move, attack, deselect a piece
     *
     * @param firstLayer  Vertical layer
     * @param secondLayer Horizontal layer
     */
    public ChessPiece MoveAttackChessPiece(int firstLayer, int secondLayer, ITier2RMIClient iTier2RMIClient, int matchID) throws RemoteException {
        ChessPiece selected = null;
        String buildString = "";
        for (int i = 0; i < chessPieces.length; i++) {

            for (int j = 0; j < chessPieces[i].length; j++) {

                if (chessPieces[i][j] != null && chessPieces[i][j].getSelected() && selected == null) {
                    selected = chessPieces[i][j].copy();
                    if (selected.getNewPosition() == null) {
                        selected.setOldPosition(new Position(i, j));
                        selected.setNewPosition(new Position(firstLayer, secondLayer));
                    } else {
                        selected.setOldPosition(chessPieces[i][j].getNewPosition());
                        selected.setNewPosition(new Position(firstLayer, secondLayer));
                    }

                }
            }
        }
        if (chessPieces[firstLayer][secondLayer] != null) {
            chessPieces[firstLayer][secondLayer].setSelected(true);
        }
        if (selected != null && selected.getOldPosition().getVerticalAxis() == firstLayer && selected.getOldPosition().getHorizontalAxis() == secondLayer) {
            chessPieces[firstLayer][secondLayer].setSelected(false);
            selected = null;
        }
        if (selected != null && selected.getColor().equals(turnColor)) {
            selected.setNewPosition(new Position(firstLayer, secondLayer));

            if (selected.getColor().equals("Black")) {
                turnColor = "White";
            } else {
                turnColor = "Black";
            }

            boolean testForNullRMI = false;
            if (iTier2RMIClient == null) {
                testForNullRMI = true;
            } else {
                testForNullRMI = iTier2RMIClient.MovePiece(selected, matchID) && iTier2RMIClient.UpdateMatchUserTurn(matchID, turnColor);
            }
            if (testForNullRMI) {

                if (chessPieces[firstLayer][secondLayer] != null) {
                    RemovedChessPieces.add(chessPieces[firstLayer][secondLayer]);
                }
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
        } else if (selected != null) {
            for (int i = 0; i < 8; i++) {

                for (int j = 0; j < 8; j++) {

                    if (chessPieces[i][j] != null) {
                        chessPieces[i][j].setSelected(false);
                    }
                }
            }
        }
        return null;
    }

    /**
     * A method to upgrade a chess piece
     *
     * @param UpgradeSelected the type of upgrade
     */
    public ChessPiece UpgradeChessPiece(String UpgradeSelected, ITier2RMIClient iTier2RMIClient, int matchID) throws RemoteException {

        for (int i = 0; i < chessPieces.length; i++) {

            for (int j = 0; j < chessPieces[i].length; j++) {

                if (chessPieces[i][j] != null && chessPieces[i][j].getSelected()) {
                    ChessPiece selected = chessPieces[i][j].copy();
                    selected.setType(UpgradeSelected);
                    selected.setSelected(false);
                    selected.setOldPosition(new Position(i, j));
                    selected.setNewPosition(new Position(i, j));
                    if (iTier2RMIClient == null){
                        chessPieces[i][j] = selected.copy();
                        return chessPieces[i][j];
                    }
                    else {
                        if (iTier2RMIClient.UpgradePiece(selected, matchID)) {
                            chessPieces[i][j] = selected.copy();
                            return chessPieces[i][j];
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns a ChessBoard
     *
     * @return ChessBoard
     */
    public ChessPiece[][] getChessBoard() {
        return chessPieces;
    }

    /**
     * Returns a color, of the chess piece which should be making the next turn
     *
     * @return turnColor
     */
    public String getTurnColor() {
        return turnColor;
    }

    /**
     * Sets a color, of the chess piece which should be making the next turn, for loading.
     *
     * @param turnColor
     */
    public void setTurnColor(String turnColor) {
        this.turnColor = turnColor;
    }

    /**
     * Returns the list of removed chess pieces
     *
     * @return list of removed chess pieces
     */
    public ArrayList<ChessPiece> getRemovedChessPieces() {
        return RemovedChessPieces;
    }

    /**
     * Calculates and returns the score for white
     *
     * @return returns the score for white
     */
    public int GetWhiteScore() {
        int result = 0;

        for (int i = 0; i < RemovedChessPieces.size(); i++) {

            if (RemovedChessPieces.get(i).getColor().equals("Black")) {

                switch (RemovedChessPieces.get(i).getType()) {
                    case "Pawn":
                        result += 1;
                        break;
                    case "Rook":
                        result += 5;
                        break;
                    case "Knight":
                        result += 3;
                        break;
                    case "Bishop":
                        result += 3;
                        break;
                    case "Queen":
                        result += 9;
                        break;
                }
            }
        }
        return result;
    }

    /**
     * Calculates and returns the score for Black
     *
     * @return returns the score for Black
     */
    public int GetBlackScore() {
        int result = 0;

        for (int i = 0; i < RemovedChessPieces.size(); i++) {

            if (RemovedChessPieces.get(i).getColor().equals("White")) {

                switch (RemovedChessPieces.get(i).getType()) {
                    case "Pawn":
                        result += 1;
                        break;
                    case "Rook":
                        result += 5;
                        break;
                    case "Knight":
                        result += 3;
                        break;
                    case "Bishop":
                        result += 3;
                        break;
                    case "Queen":
                        result += 9;
                        break;
                }
            }
        }
        return result;
    }
}
