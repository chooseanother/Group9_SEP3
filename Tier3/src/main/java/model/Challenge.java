package model;

public class Challenge {
    private User challenger;
    private User challenged;
    private String turnTime;

    public Challenge(User challenger, User challenged, String turnTime) {
        this.challenger = challenger;
        this.challenged = challenged;
        this.turnTime = turnTime;
    }

    public void setChallenger(User challenger) {
        this.challenger = challenger;
    }

    public void setChallenged(User challenged) {
        this.challenged = challenged;
    }

    public void setTurnTime(String turnTime) {
        this.turnTime = turnTime;
    }

    public User getChallenger() {
        return challenger;
    }

    public User getChallenged() {
        return challenged;
    }

    public String getTurnTime() {
        return turnTime;
    }
}
