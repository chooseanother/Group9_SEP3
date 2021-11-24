package model;


import RMI.ITier2RMIClient;
import com.google.gson.Gson;

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
        turnColor="White";

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
//            chessPieces[6][i] = chessPieces[0][7] = new ChessPiece("Pawn", white, new Position(6, i));
            chessPieces[6][i] = new ChessPiece("Pawn", white, new Position(6, i));
        }

    }

    /**
     * A method to move a chess piece from t1 to t2 and then save it in t3 database server
     * @param selected a chess piece which should be moved
     * @param iTier2RMIClient rmi connection to move a piece in t3
     * @param matchID match id in which the move is made
     * @return chesspiece moved
     * @throws RemoteException
     */
    public ChessPiece MoveAttackChessPiece(ChessPiece selected, ITier2RMIClient iTier2RMIClient, int matchID) throws RemoteException {
        String buildString="";
        if (selected != null && selected.getColor().equals(turnColor)) {
            if (selected.getColor().equals("Black")) {
                turnColor = "White";
            } else {
                turnColor = "Black";
            }
            boolean testForNullRMI = false;
            if (iTier2RMIClient == null) {
                testForNullRMI = true;
            } else {
                if(iTier2RMIClient.MovePiece(selected, matchID) && iTier2RMIClient.UpdateMatchUserTurn(matchID,turnColor)){
                   testForNullRMI= true;
                    System.out.println(turnColor+ " -----------------------------------------------------$$$$$$$$");
                }
                System.out.println("=================================>"+testForNullRMI);
            }
            if (testForNullRMI) {
                    if (chessPieces[selected.getNewPosition().getVerticalAxis()][selected.getNewPosition().getHorizontalAxis()] != null) {
                        RemovedChessPieces.add(chessPieces[selected.getNewPosition().getVerticalAxis()][selected.getNewPosition().getHorizontalAxis()]);
                    }
                   chessPieces[selected.getNewPosition().getVerticalAxis()][selected.getNewPosition().getHorizontalAxis()] = selected.copy();
                    if (selected.getOldPosition().getVerticalAxis() != chessPieces[selected.getNewPosition().getVerticalAxis()][selected.getNewPosition().getHorizontalAxis()].getNewPosition().getVerticalAxis()
                            || selected.getOldPosition().getHorizontalAxis() != chessPieces[selected.getNewPosition().getVerticalAxis()][selected.getNewPosition().getHorizontalAxis()].getNewPosition().getHorizontalAxis()) {
                        chessPieces[selected.getOldPosition().getVerticalAxis()][selected.getOldPosition().getHorizontalAxis()] = null;
                    }

                    for (int i = 0; i < 8; i++) {

                        for (int j = 0; j < 8; j++) {

                            if (chessPieces[i][j] != null) {
                                chessPieces[i][j].setSelected(false);
                            }
                        }
                    }
                    return chessPieces[selected.getNewPosition().getVerticalAxis()][selected.getNewPosition().getHorizontalAxis()];

            }


        }else if(!selected.getColor().equals(turnColor)){
            System.out.println("Turn color is not matching");
        }

//        for (int i = 0; i < chessPieces.length; i++) {
//            for (int j = 0; j < chessPieces[i].length; j++) {
//                if (chessPieces[i][j] != null) {
//                    if(chessPieces[i][j].getSelected()){
//                        System.out.println("Selected piece +"+new Gson().toJson(chessPieces[i][j]));
//                    }
//                }
//            }
//        }

        //System.out.println(buildString);
        System.out.println(new Gson().toJson(chessPieces));
        return null;
    }

    public String getTurnColor() {
        return turnColor;
    }

    public void setTurnColor(String turnColor) {
        this.turnColor = turnColor;
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

                    // dont want to persist when recreating match from move history
                    if (iTier2RMIClient != null) {
                        if (iTier2RMIClient.UpgradePiece(selected, matchID)) {
                            chessPieces[i][j] = selected.copy();
                            return chessPieces[i][j];
                        }
                    } else {
                        chessPieces[i][j] = selected.copy();
                        return chessPieces[i][j];
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
