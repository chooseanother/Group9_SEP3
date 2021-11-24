package model;

import java.io.Serializable;
import java.util.Date;

public class Match implements Serializable {
    private int matchID;
    private int tournamentID;
    private int turnTime;
    private String type;
    private boolean finished;
    private String usersTurn;
    private Date latestMove;
    private Participant whitePlayer;
    private Participant blackPlayer;

    public Match(int matchID, int tournamentID, int turnTime, String type, boolean finished, String usersTurn, Date latestMove, Participant whitePlayer, Participant blackPlayer) {
        this.matchID = matchID;
        this.tournamentID = tournamentID;
        this.turnTime = turnTime;
        this.type = type;
        this.finished = finished;
        this.usersTurn = usersTurn;
        this.latestMove = latestMove;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
    }

    public Match(int matchID, int tournamentID, int turnTime, String type, boolean finished, String usersTurn, Date latestMove) {
        this.matchID = matchID;
        this.tournamentID = tournamentID;
        this.turnTime = turnTime;
        this.type = type;
        this.finished = finished;
        this.usersTurn = usersTurn;
        this.latestMove = latestMove;
    }

    public int getMatchID() {
        return matchID;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    public int getTournamentID() {
        return tournamentID;
    }

    public void setTournamentID(int tournamentID) {
        this.tournamentID = tournamentID;
    }

    public int getTurnTime() {
        return turnTime;
    }

    public void setTurnTime(int turnTime) {
        this.turnTime = turnTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getUsersTurn() {
        return usersTurn;
    }

    public void setUsersTurn(String usersTurn) {
        this.usersTurn = usersTurn;
    }

    public Date getLatestMove() {
        return latestMove;
    }

    public void setLatestMove(Date latestMove) {
        this.latestMove = latestMove;
    }

    public Participant getWhitePlayer() {
        return whitePlayer;
    }

    public void setWhitePlayer(Participant whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    public Participant getBlackPlayer() {
        return blackPlayer;
    }

    public void setBlackPlayer(Participant blackPlayer) {
        this.blackPlayer = blackPlayer;
    }
}
