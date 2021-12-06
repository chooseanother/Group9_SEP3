package persistence;

import model.Tournament;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TournamentPersistence {
    int createTournament(String username, int turnTime, int participants) throws SQLException;

    ArrayList<Tournament> loadTournaments() throws SQLException;

    void updateTournamentNrOfParticipants(int ID, int newSize) throws SQLException;

    ArrayList<Tournament> loadTournamentsForASpecificUser(String username) throws SQLException;

    public void setTournamentOutcome(int tournamentId, boolean finished) throws SQLException;
}
