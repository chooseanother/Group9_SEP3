package persistence;

import model.Tournament;
import model.TournamentParticipation;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TournamentParticipationPersistence {
    public int CreateTournamentParticipation(String username, int tournamentID, int placement) throws SQLException;

    public ArrayList<TournamentParticipation> loadTournamentParticipants(int tournamentID) throws SQLException;

    public void UpdateParticipantsPlacement(String username, int placement, int tournamentId) throws SQLException;

    public int getNrofOriginalParticipants(int tournamentID) throws SQLException;
}
