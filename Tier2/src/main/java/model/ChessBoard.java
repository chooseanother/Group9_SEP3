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
     * A method to move a chess piece from t1 to t2 and then save it in t3 database server
     *
     * @param selected        a chess piece which should be moved
     * @param iTier2RMIClient rmi connection to move a piece in t3
     * @param matchID         match id in which the move is made
     * @return chesspiece moved
     * @throws RemoteException
     */
    public ChessPiece MoveAttackChessPiece(ChessPiece selected, ITier2RMIClient iTier2RMIClient, int matchID,String username) throws RemoteException {
        String buildString = "";
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
                ArrayList<Participant> participants = iTier2RMIClient.getParticipants(matchID);
                for(int i=0;i<participants.size();i++){
                    if(participants.get(i).getUsername().equals(username) && participants.get(i).getColor().equals(selected.getColor())) {
                        if (iTier2RMIClient.MovePiece(selected, matchID) && iTier2RMIClient.UpdateMatchUserTurn(matchID, turnColor)) {
                            //Sending of email after making the turn
//                            for (Participant p : participants) {
//                                if (p.getColor().equals(turnColor)) {
//                                    User user = iTier2RMIClient.getUser(p.getUsername());
//                                    Email email = new Email();
//                                    email.sendEmail("NotSoBusyChess@gmail.com", user.getEmail(), user.getUsername(), matchID);
//                                }
//                            }
                            testForNullRMI = true;
                        }
                    }
                }
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
     
          
        }
        return null;
    }

    /**
     * A method to upgrade a chess piece
     *
     * @param UpgradeSelected the type of upgrade
     */

    public ChessPiece UpgradeChessPiece(String UpgradeSelected, ChessPiece toUpgrade, ITier2RMIClient iTier2RMIClient, int matchID,String username) throws RemoteException {
        if(toUpgrade!=null && UpgradeSelected!=null){
            toUpgrade.setType(UpgradeSelected);
            // dont want to persist when recreating match from move history
            if (iTier2RMIClient != null) {
                ArrayList<Participant> participants = iTier2RMIClient.getParticipants(matchID);
                for (Participant p:participants){
                    if(p.getColor().equals(toUpgrade.getColor()) && p.getUsername().equals(username)){
                        if (iTier2RMIClient.UpgradePiece(toUpgrade, matchID)) {
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
    public int GetScore(String color) {
        int result = 0;

        for (ChessPiece removedChessPiece : RemovedChessPieces) {

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
