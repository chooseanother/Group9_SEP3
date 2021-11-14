package model;

/**
 * @author Nick/Rokas
 * @version 1.0
 */

public class ChessPiece {
    private String type;
    private Boolean Selected;
    private Position OldPosition;
    private Position NewPosition;

    /**
     * Creating a ChessPiece and initializing all the variables
     * @param type type
     */
    public ChessPiece(String type){
        this.type = type;
        Selected = false;
        OldPosition = new Position(0,0);
        NewPosition = new Position(0,0);
    }

    /**
     * Returns the type
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Setts the type
     * @param type type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns selected
     * @return selected
     */
    public Boolean getSelected() {
        return Selected;
    }

    /**
     * Sets selected
     * @param selected selected
     */
    public void setSelected(Boolean selected) {
        Selected = selected;
    }

    /**
     * Returns the old position
     * @return old position
     */
    public Position getOldPosition() {
        return OldPosition;
    }

    /**
     * Setts the old position
     * @param oldPosition old position
     */
    public void setOldPosition(Position oldPosition) {
        OldPosition = oldPosition;
    }

    /**
     * Returns new position
     * @return new position
     */
    public Position getNewPosition() {
        return NewPosition;
    }

    /**
     * Set new position
     * @param newPosition new position
     */
    public void setNewPosition(Position newPosition) {
        NewPosition = newPosition;
    }

    /**
     * A method to return the piece image path based on type
     * @return image path
     */
    public String getPiece(){

        switch (type.toLowerCase()) {
            //Black
            case "black-rook":
                return "Images/BRook.png";
            case "black-horse":
                return "Images/BHorse.png";
            case "black-bishop":
                return "Images/BBishop.png";
            case "black-queen":
                return "Images/BQueen.png";
            case "black-king":
                return "Images/BKing.png";
            case "black-pawn":
                return "Images/BPawn.png";

            //White
            case "white-rook":
                return "Images/WRook.png";
            case "white-horse":
                return "Images/WHorse.png";
            case "white-bishop":
                return "Images/WBishop.png";
            case "white-queen":
                return "Images/WQueen.png";
            case "white-king":
                return "Images/WKing.png";
            case "white-pawn":
                return "Images/WPawn.png";
            default:
                return "none";
        }

    }


}
