package model;


import RMI.ITier2RMIClient;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * @author group9
 * @version 1.0
 */

public class ChessBoard {
    private ChessPiece[][] chessPieces;
    private ArrayList<ChessPiece> removedChessPieces;
    private int blackScore;
    private int whiteScore;
    private String turnColor;

    /**
     * Creating a chess board and setting the default chess pieces locations
     */
    public ChessBoard() {
        chessPieces = new ChessPiece[8][8];
        removedChessPieces = new ArrayList<>();
        blackScore = 0;
        whiteScore = 0;
        turnColor = "White";

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
        }

    }

    /**
     * A method to move a chess piece from t1 to t2 and then save it in t3 database server
     *
     * @param selected        a chess piece which should be moved
     * @param iTier2RMIClient rmi connection to move a piece in t3
     * @param matchID         match id in which the move is made
     * @return a chess piece that is moved or null
     */
    public ChessPiece moveAttackChessPiece(ChessPiece selected, ITier2RMIClient iTier2RMIClient, int matchID, String username) throws RemoteException {
        if (selected != null && selected.getColor().equals(turnColor) && chessPieces[selected.getOldPosition().getVerticalAxis()][selected.getOldPosition().getHorizontalAxis()]!=null && chessPieces[selected.getOldPosition().getVerticalAxis()][selected.getOldPosition().getHorizontalAxis()].getColor().equals(selected.getColor())) {
            turnColor = updateColor(selected.getColor());
            boolean testForNullRMI = false;
            if (iTier2RMIClient == null) {
                testForNullRMI = true;
            } else {
                ArrayList<Participant> participants = iTier2RMIClient.getParticipants(matchID);
                for(int i=0;i<participants.size();i++){
                    if(participants.get(i).getUsername().equals(username) && participants.get(i).getColor().equals(selected.getColor())) {
                        if (iTier2RMIClient.movePiece(selected, matchID) && iTier2RMIClient.updateMatchUsersTurn(matchID, turnColor)) {
                            testForNullRMI = true;
                        }
                    }
                }
            }
            if (testForNullRMI) {
                addRemovedChessPieces(chessPieces[selected.getNewPosition().getVerticalAxis()][selected.getNewPosition().getHorizontalAxis()]);
                return performMove(selected);

            }
     
          
        }
        return null;
    }

    /**
     * Moves chess pieces from old position to new position
     * @param chessPiece moved chesspiece
     */
    public ChessPiece performMove(ChessPiece chessPiece){
        chessPieces[chessPiece.getNewPosition().getVerticalAxis()][chessPiece.getNewPosition().getHorizontalAxis()] = chessPiece.copy();
        chessPieces[chessPiece.getOldPosition().getVerticalAxis()][chessPiece.getOldPosition().getHorizontalAxis()] = null;
        unselectAllPieces();
        return chessPieces[chessPiece.getNewPosition().getVerticalAxis()][chessPiece.getNewPosition().getHorizontalAxis()];
    }

    /**
     * Unselects all pieces on the chessboard
     */
    public void unselectAllPieces(){
        for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 8; j++) {

                if (chessPieces[i][j] != null) {
                    chessPieces[i][j].setSelected(false);
                }
            }
        }
    }
    /**
     * Updates which color should move after this move.
     * @param turnColor which color moved
     * @return which color should move after
     */
    public String updateColor(String turnColor){
        if (turnColor.equals("Black")) {
            return "White";
        } else {
            return  "Black";
        }
    }

    /**
     * Updates the list of removed chess pieces
     * @param chessPiece the piece which was removed
     */
    public void addRemovedChessPieces(ChessPiece chessPiece){
        if (chessPiece!=null) {
            removedChessPieces.add(chessPiece);
        }
    }

    /**
     * A method to upgrade a chess piece
     *
     * @param upgradeSelected the type of upgrade
     */

    public ChessPiece upgradeChessPiece(String upgradeSelected, ChessPiece toUpgrade, ITier2RMIClient iTier2RMIClient, int matchID, String username) throws RemoteException {
        if(toUpgrade!=null && upgradeSelected!=null){
            toUpgrade.setType(upgradeSelected);
            if (iTier2RMIClient != null) {
                ArrayList<Participant> participants = iTier2RMIClient.getParticipants(matchID);
                for (Participant p:participants){
                    if(p.getColor().equals(toUpgrade.getColor()) && p.getUsername().equals(username)){
                        if (iTier2RMIClient.upgradePiece(toUpgrade, matchID)) {
                            chessPieces[toUpgrade.getNewPosition().getVerticalAxis()][toUpgrade.getNewPosition().getHorizontalAxis()] = toUpgrade.copy();
                            return chessPieces[toUpgrade.getNewPosition().getVerticalAxis()][toUpgrade.getNewPosition().getHorizontalAxis()];
                        }
                    }
                }
            } else {
                chessPieces[toUpgrade.getNewPosition().getVerticalAxis()][toUpgrade.getNewPosition().getHorizontalAxis()] = toUpgrade.copy();
                return chessPieces[toUpgrade.getNewPosition().getVerticalAxis()][toUpgrade.getNewPosition().getHorizontalAxis()];
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
     * @return turnColor color of the user that should move next
     */
    public String getTurnColor() {
        return turnColor;
    }

    /**
     * Sets a color, of the chess piece which should be making the next turn, for loading.
     *
     * @param turnColor color of the user that should move next
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
        return removedChessPieces;
    }

    /**
     * Calculates and returns the score for white
     *
     * @return returns the score for white
     */
    public int getScore(String color) {
        int result = 0;

        for (ChessPiece removedChessPiece : removedChessPieces) {

            if (removedChessPiece.getColor().equals(color)) {

                switch (removedChessPiece.getType()) {
                    case "Pawn" -> result += 1;
                    case "Rook" -> result += 5;
                    case "Knight", "Bishop" -> result += 3;
                    case "Queen" -> result += 9;
                }
            }
        }
        return result;
    }
}
