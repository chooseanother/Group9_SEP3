package model;

import java.io.Serializable;

/**
 * @author group9
 * @version 1.0
 */

public class Match implements Serializable {

    private int matchID;
    private int tournamentID;
    private int turnTime;
    private String type;
    private boolean finished;
    private String usersTurn;
    private long latestMove;
    private Participant whitePlayer;
    private Participant blackPlayer;


    /**
     * Creates a match
     * @param matchID match id
     * @param tournamentID tournament id
     * @param turnTime turn time
     * @param type type
     * @param finished status
     * @param usersTurn user turn
     * @param latestMove last move
     * @param whitePlayer white player
     * @param blackPlayer black player
     */
    public Match(int matchID, int tournamentID, int turnTime, String type, boolean finished, String usersTurn, long latestMove, Participant whitePlayer, Participant blackPlayer) {
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

    /**
     * Creates a match
     *
     * @param matchID      match id
     * @param tournamentID tournament id
     * @param turnTime     turn time
     * @param type         type
     * @param finished     status
     * @param usersTurn    user turn
     * @param latestMove   last move
     */
    public Match(int matchID, int tournamentID, int turnTime, String type, boolean finished, String usersTurn, long latestMove) {
        this.matchID = matchID;
        this.tournamentID = tournamentID;
        this.turnTime = turnTime;
        this.type = type;
        this.finished = finished;
        this.usersTurn = usersTurn;
        this.latestMove = latestMove;
    }

    /**
     * Returns the Match ID
     * @return Match ID
     */
    public int getMatchID() {
        return matchID;
    }

    /**
     * Sets the Match ID
     * @param matchID Match ID
     */
    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    /**
     * Returns the Tournament ID
     * @return Tournament ID
     */
    public int getTournamentID() {
        return tournamentID;
    }

    /**
     * Sets the Tournament ID
     * @param tournamentID Tournament ID
     */
    public void setTournamentID(int tournamentID) {
        this.tournamentID = tournamentID;
    }

    /**
     * Returns the Turn Time
     * @return Turn Time
     */
    public int getTurnTime() {
        return turnTime;
    }

    /**
     * Sets the Turn Time
     * @param turnTime Turn time
     */
    public void setTurnTime(int turnTime) {
        this.turnTime = turnTime;
    }

    /**
     * Returns the type
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the Type
     * @param type type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns if the match is finished
     * @return match status
     */
    public boolean getFinished() {
        return finished;
    }

    /**
     * Sets the match to finished
     * @param finished finished
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    /**
     * Gets the who's turn it is
     * @return users turn
     */
    public String getUsersTurn() {
        return usersTurn;
    }

    /**
     * Sets the users turn
     * @param usersTurn users turn
     */
    public void setUsersTurn(String usersTurn) {
        this.usersTurn = usersTurn;
    }

    /**
     * gets the latest move
     * @return latest move
     */
    public long getLatestMove() {
        return latestMove;
    }

    /**
     * Sets the Latest Move
     * @param latestMove latest move
     */
    public void setLatestMove(long latestMove) {
        this.latestMove = latestMove;
    }

    /**
     * Returns the White Player
     * @return a white player
     */
    public Participant getWhitePlayer() {
        return whitePlayer;
    }

    /**
     * Sets the white player
     * @param whitePlayer white player
     */
    public void setWhitePlayer(Participant whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    /**
     * Returns the Black Player
     * @return black player
     */
    public Participant getBlackPlayer() {
        return blackPlayer;
    }

    /**
     * Sets the black player
     * @param blackPlayer black player
     */
    public void setBlackPlayer(Participant blackPlayer) {
        this.blackPlayer = blackPlayer;
    }
}
