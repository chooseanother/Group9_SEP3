package persistence;

import model.TournamentParticipation;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TournamentParticipationPersistence {
    int createTournamentParticipation(String username, int tournamentID, int placement) throws SQLException;

    ArrayList<TournamentParticipation> loadTournamentParticipants(int tournamentID) throws SQLException;

    void updateParticipantsPlacement(String username, int placement, int tournamentId) throws SQLException;

    int getNrOfOriginalParticipants(int tournamentID) throws SQLException;

    ArrayList<TournamentParticipation> getTopPlayersInATournament(int tournamentID) throws SQLException;
}
