package model;

/**
 * @author Nick/Rokas
 * @version 1.0
 */

public class ChessPiece {
    private String type;
    private Boolean selected;
    private Position oldPosition;
    private Position newPosition;
    private String color;

    /**
     * Creating a ChessPiece and initializing all the variables
     * @param type type
     */
    public ChessPiece(String type,String color,Position newPosition){
        this.type = type;
        selected = false;
        oldPosition = null;
        this.newPosition=newPosition;
        this.color=color;
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
        return selected;
    }

    /**
     * Sets selected
     * @param selected selected
     */
    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    /**
     * Returns the old position
     * @return old position
     */
    public Position getOldPosition() {
        return oldPosition;
    }

    /**
     * Setts the old position
     * @param oldPosition old position
     */
    public void setOldPosition(Position oldPosition) {
        this.oldPosition = oldPosition;
    }

    /**
     * Returns new position
     * @return new position
     */
    public Position getNewPosition() {
        return newPosition;
    }

    /**
     * Returns the color of a piece
     * @return color
     */
    public String getColor(){
        return color;
    }
    /**
     * Set new position
     * @param newPosition new position
     */
    public void setNewPosition(Position newPosition) {
        this.newPosition = newPosition;
    }

    /**
     * A method to return the piece image path based on type
     * @return image path
     */
    public String getPiece(){

        switch (color.toLowerCase()+"-"+type.toLowerCase()) {
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
    public ChessPiece copy (){
            ChessPiece chessPiece = new ChessPiece(type,color,newPosition);
            chessPiece.setOldPosition(oldPosition);
            return chessPiece;

    }


}
