package model;

import java.io.Serializable;

/**
 * @author group9
 * @version 1.0
 */

public class Challenge implements Serializable {
    private String challenger;
    private String challenged;
    private int turnTime;


    /**
     * Creating a Challenge
     *
     * @param challenger challenger
     * @param challenged challenged
     * @param turnTime   turn time
     */
    public Challenge(String challenger, String challenged, int turnTime) {
        this.challenger = challenger;
        this.challenged = challenged;
        this.turnTime = turnTime;
    }

    /**
     * setts the challenger
     *
     * @param challenger challenger
     */
    public void setChallenger(String challenger) {
        this.challenger = challenger;
    }

    /**
     * setts the challenged
     *
     * @param challenged challenger
     */
    public void setChallenged(String challenged) {
        this.challenged = challenged;
    }

    /**
     * setts turn time
     *
     * @param turnTime turn time
     */
    public void setTurnTime(int turnTime) {
        this.turnTime = turnTime;
    }

    /**
     * Returns the challenger
     *
     * @return challenger
     */
    public String getChallenger() {
        return challenger;
    }

    /**
     * Returns the challenged
     *
     * @return challenged
     */
    public String getChallenged() {
        return challenged;
    }

    /**
     * Returns the turn time
     *
     * @return turn time
     */
    public int getTurnTime() {
        return turnTime;
    }
}
