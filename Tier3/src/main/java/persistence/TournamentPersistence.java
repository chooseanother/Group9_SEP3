package persistence;

import model.Tournament;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TournamentPersistence {
    public int createTournament(String username, int turnTime, int participants) throws SQLException;

    public ArrayList<Tournament> loadTournaments() throws SQLException;

    public void UpdateTournamentNrOfParticipants(int ID, int newSize) throws SQLException;
}
