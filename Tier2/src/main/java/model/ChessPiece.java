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
     * Creating a chess piece with more variables
     * @param type
     * @param color
     * @param newPosition
     */
    public ChessPiece(String type,String color,Position oldPosition,Position newPosition){
        this.type = type;
        selected = false;
        this.oldPosition = oldPosition;
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
     * Makes a copy of chesspiece for aggregation
     * @return copy of chess piece
     */
    public ChessPiece copy (){
            ChessPiece chessPiece = new ChessPiece(type,color,newPosition);
            chessPiece.setOldPosition(oldPosition);
            return chessPiece;

    }


}
