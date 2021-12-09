package model;

import java.io.Serializable;

/**
 * @author group9
 * @version 1.0
 */

public class Participant implements Serializable {
    private String username;
    private int matchId;
    private String color;
    private String outcome;

    /**
     * Creates the match participant
     *
     * @param username username
     * @param matchId  match id
     * @param color    color
     * @param outcome  outcome
     */
    public Participant(String username, int matchId, String color, String outcome) {
        this.username = username;
        this.matchId = matchId;
        this.color = color;
        this.outcome = outcome;
    }

    /**
     * Returns the username
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }


    /**
     * Sets the username
     *
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the match id
     *
     * @return match id
     */
    public int getMatchId() {
        return matchId;
    }

    /**
     * Sets match id
     *
     * @param matchId match id
     */
    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    /**
     * Returns color
     *
     * @return color
     */
    public String getColor() {
        return color;
    }

    /**
     * Set color
     *
     * @param color color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Returns outcome
     *
     * @return outcome
     */
    public String getOutcome() {
        return outcome;
    }

    /**
     * Sets the outcome
     *
     * @param outcome outcome
     */
    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }
}
