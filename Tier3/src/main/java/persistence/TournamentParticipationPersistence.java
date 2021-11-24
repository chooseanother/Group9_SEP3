package persistence;

import model.Tournament;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TournamentParticipationPersistence {
    public int CreateTournamentParticipation(String username, int tournamentID, int placement) throws SQLException;

    public ArrayList<String> loadUsernamesOfPlayersInATournament(int tournamentID) throws SQLException;
}
