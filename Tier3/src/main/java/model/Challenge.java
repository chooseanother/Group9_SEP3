package model;

import java.io.Serializable;

public class Challenge implements Serializable {
    private String challenger;
    private String challenged;
    private int turnTime;

    public Challenge(String challenger, String challenged, int turnTime) {
        this.challenger = challenger;
        this.challenged = challenged;
        this.turnTime = turnTime;
    }

    public void setChallenger(String challenger) {
        this.challenger = challenger;
    }

    public void setChallenged(String challenged) {
        this.challenged = challenged;
    }

    public void setTurnTime(int turnTime) {
        this.turnTime = turnTime;
    }

    public String getChallenger() {
        return challenger;
    }

    public String getChallenged() {
        return challenged;
    }

    public int getTurnTime() {
        return turnTime;
    }
}
