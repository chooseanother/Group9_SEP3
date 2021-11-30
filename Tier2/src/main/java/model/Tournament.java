package model;

import java.io.Serializable;

public class Tournament implements Serializable {
    private String creator;
    private int turnTime;
    private int NrOfParticipants;
    private int tournamentId;

    public Tournament(String creator, int turnTime, int nrOfParticipants){
        this.creator = creator;
        this.turnTime = turnTime;
        this.NrOfParticipants = nrOfParticipants;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getTurnTime() {
        return turnTime;
    }

    public void setTurnTime(int turnTime) {
        this.turnTime = turnTime;
    }

    public int getNrOfParticipants() {
        return NrOfParticipants;
    }

    public void setNrOfParticipants(int participants) {
        this.NrOfParticipants = participants;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

}
