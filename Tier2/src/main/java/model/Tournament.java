package model;

import java.io.Serializable;

/**
 * @author group9
 * @version 1.0
 */

public class Tournament implements Serializable {
    private String creator;
    private int turnTime;
    private int NrOfParticipants;
    private int tournamentId;
    private String top3Players;

    /**
     * Creates a Tournament
     *
     * @param creator          creator
     * @param turnTime         turn time
     * @param nrOfParticipants number of participants
     */
    public Tournament(String creator, int turnTime, int nrOfParticipants){
        this.creator = creator;
        this.turnTime = turnTime;
        this.NrOfParticipants = nrOfParticipants;
    }

    /**
     * Returns the creator of the tournament
     *
     * @return creator of the tournament
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Sets the creator of the tournament
     *
     * @param creator creator of the tournament
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * Returns the turn time
     *
     * @return turn time
     */
    public int getTurnTime() {
        return turnTime;
    }

    /**
     * Sets the turn time
     *
     * @param turnTime turn time
     */
    public void setTurnTime(int turnTime) {
        this.turnTime = turnTime;
    }

    /**
     * Returns the number of participants
     *
     * @return number of participants
     */
    public int getNrOfParticipants() {
        return NrOfParticipants;
    }

    /**
     * Sets the number of participants
     *
     * @param participants number of participants
     */
    public void setNrOfParticipants(int participants) {
        this.NrOfParticipants = participants;
    }

    /**
     * Returns the Tournament id
     *
     * @return Tournament id
     */
    public int getTournamentId() {
        return tournamentId;
    }

    /**
     * Sets the Tournament id
     *
     * @param tournamentId Tournament id
     */
    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    /**
     * @return top 3 players
     */
    public String getTop3Players() {
        return top3Players;
    }

    /**
     * Sets the top 3 players
     *
     * @param top3Players top 3 players
     */
    public void setTop3Players(String top3Players) {
        this.top3Players = top3Players;
    }
}
