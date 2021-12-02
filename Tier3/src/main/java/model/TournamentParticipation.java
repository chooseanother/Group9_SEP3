package model;

import java.io.Serializable;

/**
 * @author group9
 * @version 1.0
 */

public class TournamentParticipation implements Serializable {
    private String username;
    private int tournamentId;
    private int placement;

    /**
     * Creating a Tournament participation
     * @param username username
     * @param tournamentId tournament id
     * @param placement placement
     */
    public TournamentParticipation(String username, int tournamentId, int placement){
        this.username = username;
        this.tournamentId = tournamentId;
        this.placement = placement;
    }

    /**
     * Returns the Tournament id
     * @return tournament id
     */
    public int getTournamentId() {
        return tournamentId;
    }

    /**
     * Sets the tournament id
     * @param tournamentId tournament id
     */
    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    /**
     * Returns the placement
     * @return placement
     */
    public int getPlacement() {
        return placement;
    }

    /**
     * Sets the placement
     * @param placement placement
     */
    public void setPlacement(int placement) {
        this.placement = placement;
    }

    /**
     * Returns the username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username
     * @param username  username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * To String method display the participation
     * @return display the participation
     */
    @Override public String toString (){
        return username + " " + tournamentId + " " + placement;
    }
}
