package model;

public class TournamentParticipation {
    private String username;
    private int tournamentId;
    private int placement;

    public TournamentParticipation(String username, int tournamentId, int placement){
        this.username = username;
        this.tournamentId = tournamentId;
        this.placement = placement;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    public int getPlacement() {
        return placement;
    }

    public void setPlacement(int placement) {
        this.placement = placement;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
