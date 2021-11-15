package model;


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
        chessPieces[0][0] = new ChessPiece("black-rook");
        chessPieces[0][1] = new ChessPiece("black-horse");
        chessPieces[0][2] = new ChessPiece("black-bishop");
        chessPieces[0][3] = new ChessPiece("black-queen");
        chessPieces[0][4] = new ChessPiece("black-king");
        chessPieces[0][5] = new ChessPiece("black-bishop");
        chessPieces[0][6] = new ChessPiece("black-horse");
        chessPieces[0][7] = new ChessPiece("black-rook");

        for (int i = 0; i < 8; i++) {
            chessPieces[1][i] = new ChessPiece("black-pawn");
        }

        //white
        chessPieces[7][0] = new ChessPiece("white-rook");
        chessPieces[7][1] = new ChessPiece("white-horse");
        chessPieces[7][2] = new ChessPiece("white-bishop");
        chessPieces[7][3] = new ChessPiece("white-queen");
        chessPieces[7][4] = new ChessPiece("white-king");
        chessPieces[7][5] = new ChessPiece("white-bishop");
        chessPieces[7][6] = new ChessPiece("white-horse");
        chessPieces[7][7] = new ChessPiece("white-rook");

        for (int i = 0; i < 8; i++) {
            chessPieces[6][i] = new ChessPiece("white-pawn");
        }

    }

    /**
     * A method to handle chess piece clicks. It can select, move, attack, deselect a piece
     *
     * @param firstLayer  Vertical layer
     * @param secondLayer Horizontal layer
     */
    public ChessPiece HandleClick(int firstLayer, int secondLayer) {
        ChessPiece selected = null;
        String buildString = "";
        for (int i = 0; i < chessPieces.length; i++) {

            for (int j = 0; j < chessPieces[i].length; j++) {

                if (chessPieces[i][j] != null && chessPieces[i][j].getSelected() && selected == null) {

                    if (chessPieces[i][j].getNewPosition() == null) {
                        chessPieces[i][j].setOldPosition(new Position(i, j));
                    } else {
                        chessPieces[i][j].setOldPosition(chessPieces[i][j].getNewPosition());
                    }
                    selected = chessPieces[i][j];
                }
            }
        }
        if (chessPieces[firstLayer][secondLayer] != null) {
            chessPieces[firstLayer][secondLayer].setSelected(true);
        }
        if (selected != null) {
            selected.setNewPosition(new Position(firstLayer, secondLayer));
            chessPieces[firstLayer][secondLayer] = selected.copy();
            chessPieces[firstLayer][secondLayer].setOldPosition(selected.getOldPosition());
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
        if (selected != null) {
            return selected;
        } else {
            return null;
        }
    }

    /**
     * A method to upgrade a chess piece
     *
     * @param UpgradeSelected the type of upgrade
     */
    public ChessPiece UpgradeChessPiece(String UpgradeSelected) {

        for (int i = 0; i < chessPieces.length; i++) {

            for (int j = 0; j < chessPieces[i].length; j++) {

                if (chessPieces[i][j] != null && chessPieces[i][j].getSelected()) {

                    if (chessPieces[i][j].getType().contains("black")) {
                        chessPieces[i][j].setType("black-" + UpgradeSelected);
                        chessPieces[i][j].setSelected(false);
                        chessPieces[i][j].setOldPosition(new Position(i,j));
                        return chessPieces[i][j];
                    } else {
                        chessPieces[i][j].setType("white-" + UpgradeSelected);
                        chessPieces[i][j].setOldPosition(new Position(i,j));
                        chessPieces[i][j].setSelected(false);
                        return chessPieces[i][j];
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns the selected chess piece
     * @return piece
     */
//    public ChessPiece getSelected(){
//        return selected;
//    }
}
