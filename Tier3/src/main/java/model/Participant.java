package model;

import java.io.Serializable;

public class Participant implements Serializable {
    private String username;
    private int matchId;
    private String color;
    private String outcome;

    public Participant(String username, int matchId, String color, String outcome) {
        this.username = username;
        this.matchId = matchId;
        this.color = color;
        this.outcome = outcome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }
}
