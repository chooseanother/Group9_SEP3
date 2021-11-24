package model;

import java.io.Serializable;

public class Move implements Serializable {
    private int moveId;
    private int matchId;
    private String piece;
    private String color;
    private String startPosition;
    private String endPosition;

    public Move(String piece, String color, String startPosition, String endPosition) {
        this.piece = piece;
        this.color = color;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public Move(int moveId, int matchId, String piece, String color, String startPosition, String endPosition) {
        this.moveId = moveId;
        this.matchId = matchId;
        this.piece = piece;
        this.color = color;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setPiece(String piece) {
        this.piece = piece;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setStartPosition(String startPosition) {
        this.startPosition = startPosition;
    }

    public void setEndPosition(String endPosition) {
        this.endPosition = endPosition;
    }

    public String getPiece() {
        return piece;
    }

    public String getColor() {
        return color;
    }

    public String getStartPosition() {
        return startPosition;
    }

    public String getEndPosition() {
        return endPosition;
    }

    public int getMoveId() {
        return moveId;
    }

    public void setMoveId(int moveId) {
        this.moveId = moveId;
    }
}
