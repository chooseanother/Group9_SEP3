package model;

import java.io.Serializable;

/**
 * @author group9
 * @version 1.0
 */

public class Move implements Serializable {
    private int moveId;
    private int matchId;
    private String piece;
    private String color;
    private String startPosition;
    private String endPosition;

    /**
     * Creating a move
     * @param piece piece
     * @param color color
     * @param startPosition start position
     * @param endPosition end position
     */
    public Move(String piece, String color, String startPosition, String endPosition) {
        this.piece = piece;
        this.color = color;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    /**
     * Creating a move
     * @param matchId match id
     * @param piece         piece
     * @param color         color
     * @param startPosition start position
     * @param endPosition   end position
     */
    public Move(int moveId, int matchId, String piece, String color, String startPosition, String endPosition) {
        this.moveId = moveId;
        this.matchId = matchId;
        this.piece = piece;
        this.color = color;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    /**
     * Sets he match id
     * @param matchId match id
     */
    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    /**
     * Returns the Match Id
     * @return MatchID
     */
    public int getMatchId() {
        return matchId;
    }

    /**
     * Sets the piece
     * @param piece piece
     */
    public void setPiece(String piece) {
        this.piece = piece;
    }

    /**
     * Sets the color
     * @param color color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Set a Start Position
     * @param startPosition start position
     */
    public void setStartPosition(String startPosition) {
        this.startPosition = startPosition;
    }

    /**
     * Set End position
     * @param endPosition end position
     */
    public void setEndPosition(String endPosition) {
        this.endPosition = endPosition;
    }

    /**
     * Returns the pieces
     * @return pieces
     */
    public String getPiece() {
        return piece;
    }

    /**
     * Returns the color
     * @return color
     */
    public String getColor() {
        return color;
    }

    /**
     * Gets the Start Position
     * @return Start Position
     */
    public String getStartPosition() {
        return startPosition;
    }

    /**
     * Get the end position
     * @return end position
     */
    public String getEndPosition() {
        return endPosition;
    }

    /**
     * Returns the Move id
     * @return move id
     */
    public int getMoveId() {
        return moveId;
    }

    /**
     * Sets the move id
     * @param moveId move id
     */
    public void setMoveId(int moveId) {
        this.moveId = moveId;
    }
}
