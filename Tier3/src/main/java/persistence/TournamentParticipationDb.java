package persistence;

import model.TournamentParticipation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author group9
 * @version 1.0
 */

public class TournamentParticipationDb implements TournamentParticipationPersistence{

    /**
     * Create a tournament participation in the database
     * @param username username
     * @param tournamentID tournament id
     * @return id
     * @throws SQLException SQLException
     */
    @Override
    public int createTournamentParticipation(String username, int tournamentID) throws SQLException {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement2 = connection.prepareStatement("INSERT INTO TOURNAMENT_PARTICIPATION (USERNAME, TOURNAMENTID) VALUES (?,?)");
            statement2.setString(1, username);
            statement2.setInt(2, tournamentID);
            statement2.executeUpdate();
        }
        return 1;
    }

    /**
     * Loads tournaments from the database
     * @param tournamentID id
     * @return tournaments
     * @throws SQLException SQLException
     */
    @Override
    public ArrayList<TournamentParticipation> loadTournamentParticipants(int tournamentID) throws SQLException {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select * from TOURNAMENT_PARTICIPATION where TOURNAMENTID = ? AND PLACEMENT = 0");
            statement.setInt(1, tournamentID);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<TournamentParticipation> tournamentParticipations = new ArrayList<>();

            while (resultSet.next()) {
                String username = resultSet.getString("USERNAME");
                int TournamentId = resultSet.getInt("TOURNAMENTID");
                int placement = resultSet.getInt("PLACEMENT");
                tournamentParticipations.add(new TournamentParticipation(username, TournamentId, placement));
            }
            return tournamentParticipations;
        }
    }

    /**
     * Updates the participants placement in the database
     * @param username username
     * @param placement placement
     * @param tournamentId id
     * @throws SQLException  SQLException
     */
    @Override
    public void updateParticipantsPlacement(String username, int placement, int tournamentId) throws SQLException {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE TOURNAMENT_PARTICIPATION SET PLACEMENT = ? WHERE USERNAME = ? AND TOURNAMENTID = ?");
            statement.setInt(1, placement);
            statement.setString(2, username);
            statement.setInt(3, tournamentId);
            statement.executeUpdate();
        }
    }

    /**
     * Returns the top 3 players from the database
     * @param tournamentID id
     * @return top 3 players
     * @throws SQLException SQLException
     */
    @Override
    public ArrayList<TournamentParticipation> getTopPlayersInATournament(int tournamentID) throws SQLException {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select TOURNAMENT_PARTICIPATION.USERNAME, TOURNAMENT_PARTICIPATION.TOURNAMENTID, PLACEMENT from tournament_participation join TOURNAMENT T on TOURNAMENT_PARTICIPATION.TOURNAMENTID = T.TOURNAMENTID where T.TOURNAMENTID = ? order by PLACEMENT asc;");
            statement.setInt(1, tournamentID);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<TournamentParticipation> topPlayers = new ArrayList<>();

            while (resultSet.next()) {
                String username = resultSet.getString("USERNAME");
                int TournamentId = resultSet.getInt("TOURNAMENTID");
                int placement = resultSet.getInt("PLACEMENT");
                topPlayers.add(new TournamentParticipation(username, TournamentId, placement));
            }
            return topPlayers;
        }
    }

}
