package model;

import java.util.Date;

public class Match {
    private int matchID;
    private int tournamentID;
    private int turnTime;
    private String type;
    private String outcome;
    private String finished;
    private String UsersTurn;
    private Date latestMove;
    private String whitePlayer;
    private String blackPlayer;
    private ChessBoard chessBoard;

    public Match(int matchID, int tournamentID, int turnTime, String type, String outcome, String finished, String usersTurn, Date latestMove, String whitePlayer, String blackPlayer) {
        this.matchID = matchID;
        this.tournamentID = tournamentID;
        this.turnTime = turnTime;
        this.type = type;
        this.outcome = outcome;
        this.finished = finished;
        UsersTurn = usersTurn;
        this.latestMove = latestMove;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
    }

    public Match(int matchID, int turnTime, String type, String outcome, String finished, String usersTurn, Date latestMove, String whitePlayer, String blackPlayer) {
        this.matchID = matchID;
        this.turnTime = turnTime;
        this.type = type;
        this.outcome = outcome;
        this.finished = finished;
        UsersTurn = usersTurn;
        this.latestMove = latestMove;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
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

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getFinished() {
        return finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }

    public String getUsersTurn() {
        return UsersTurn;
    }

    public void setUsersTurn(String usersTurn) {
        UsersTurn = usersTurn;
    }

    public Date getLatestMove() {
        return latestMove;
    }

    public void setLatestMove(Date latestMove) {
        this.latestMove = latestMove;
    }

    public String getWhitePlayer() {
        return whitePlayer;
    }

    public void setWhitePlayer(String whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    public String getBlackPlayer() {
        return blackPlayer;
    }

    public void setBlackPlayer(String blackPlayer) {
        this.blackPlayer = blackPlayer;
    }
}
