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
     * A method to handle chess piece moving methods.
     *
     * @param selected        a chess piece which should be moved
     * @param iTier2RMIClient rmi connection to move a piece in t3
     * @param matchID         match id in which the move is made
     * @return a chess piece that is moved or null
     * @throws RemoteException rmi exception to be shown
     */
    public ChessPiece handleMoveAttack(ChessPiece selected, ITier2RMIClient iTier2RMIClient, int matchID, String username) throws RemoteException {
        if (selected != null && selected.getColor().equals(turnColor) && chessPieces[selected.getOldPosition().getVerticalAxis()][selected.getOldPosition().getHorizontalAxis()] != null && chessPieces[selected.getOldPosition().getVerticalAxis()][selected.getOldPosition().getHorizontalAxis()].getColor().equals(selected.getColor())) {
            turnColor = updateColor(selected.getColor());
            if (loadingOrPlayerMove(iTier2RMIClient, matchID, username, selected)) {
                addRemovedChessPieces(chessPieces[selected.getNewPosition().getVerticalAxis()][selected.getNewPosition().getHorizontalAxis()]);
                return performMove(selected);
            }
        }
        return null;
    }

    /**
     * Returns a boolean which indicates if the move should be made
     *
     * @param iTier2RMIClient rmi connection to data server
     * @param matchID         indicates in which match the move was made
     * @param username        indicates by whom the move was made
     * @param chessPieceMoved indicates which piece was moved
     * @return a boolean which shows if the move is legal
     * @throws RemoteException rmi exception to be shown
     */
    private boolean loadingOrPlayerMove(ITier2RMIClient iTier2RMIClient, int matchID, String username, ChessPiece chessPieceMoved) throws RemoteException {
        boolean testForNullRMI = false;
        if (iTier2RMIClient == null) {
            testForNullRMI = true;
        } else {
                if (validateUserColor(iTier2RMIClient,matchID,username,chessPieceMoved)) {
                    if (iTier2RMIClient.movePiece(chessPieceMoved, matchID) && iTier2RMIClient.updateMatchUsersTurn(matchID, turnColor)) {
                        testForNullRMI = true;

                }
            }
        }
        return testForNullRMI;
    }

    /**
     * Moves chess pieces from old position to new position
     *
     * @param chessPiece moved chesspiece
     */
    private ChessPiece performMove(ChessPiece chessPiece) {
        chessPieces[chessPiece.getNewPosition().getVerticalAxis()][chessPiece.getNewPosition().getHorizontalAxis()] = chessPiece.copy();
        chessPieces[chessPiece.getOldPosition().getVerticalAxis()][chessPiece.getOldPosition().getHorizontalAxis()] = null;
        unselectAllPieces();
        return chessPieces[chessPiece.getNewPosition().getVerticalAxis()][chessPiece.getNewPosition().getHorizontalAxis()];
    }

    /**
     * Unselects all pieces on the chessboard
     */
    private void unselectAllPieces() {
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
     *
     * @param turnColor which color moved
     * @return which color should move after
     */
    private String updateColor(String turnColor) {
        if (turnColor.equals("Black")) {
            return "White";
        } else {
            return "Black";
        }
    }

    /**
     * Updates the list of removed chess pieces
     *
     * @param chessPiece the piece which was removed
     */
    private void addRemovedChessPieces(ChessPiece chessPiece) {
        if (chessPiece != null) {
            removedChessPieces.add(chessPiece);
        }
    }

    /**
     * A method to upgrade a chess piece
     *
     * @param upgradeSelected the type of upgrade
     */

    public ChessPiece handleUpgradeChessPiece(String upgradeSelected, ChessPiece toUpgrade, ITier2RMIClient iTier2RMIClient, int matchID, String username) throws RemoteException {
        if (toUpgrade != null && upgradeSelected != null) {
            toUpgrade.setType(upgradeSelected);
            if (iTier2RMIClient != null) {
                    if (validateUserColor(iTier2RMIClient,matchID,username,toUpgrade)) {
                        if (iTier2RMIClient.upgradePiece(toUpgrade, matchID)) {
                            return upgradeChessPiece(toUpgrade,upgradeSelected);

                    }
                }
            } else {
                return upgradeChessPiece(toUpgrade,upgradeSelected);
            }
        }

        return null;
    }

    /**
     * Checks if the piece selected belongs to the user
     * @param iTier2RMIClient rmi connection to data server
     * @param matchID         indicates in which match the move was made
     * @param username        indicates by whom the move was made
     * @param chessPiece indicates which piece was moved
     * @return a boolean which indicates if the color is valid
     * @throws RemoteException rmi exception to be shown
     */
    private boolean validateUserColor(ITier2RMIClient iTier2RMIClient, int matchID, String username, ChessPiece chessPiece) throws RemoteException{
        ArrayList<Participant> participants = iTier2RMIClient.getParticipants(matchID);
        for (Participant p : participants) {
            if (p.getColor().equals(chessPiece.getColor()) && p.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Upgrades
     * @param toUpgrade
     * @return
     */
    private ChessPiece upgradeChessPiece(ChessPiece toUpgrade,String upgradeSelected){

        chessPieces[toUpgrade.getNewPosition().getVerticalAxis()][toUpgrade.getNewPosition().getHorizontalAxis()] = toUpgrade.copy();
        unselectAllPieces();
        return chessPieces[toUpgrade.getNewPosition().getVerticalAxis()][toUpgrade.getNewPosition().getHorizontalAxis()];
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
