package persistence;

import model.Tournament;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author group9
 * @version 1.0
 */

public class TournamentDB implements TournamentPersistence {

    /**
     * Creates a tournament in the  database
     * @param username username
     * @param turnTime username
     * @param participants participants
     * @return id
     * @throws SQLException SQLException
     */
    @Override
    public int createTournament(String username, int turnTime, int participants) throws SQLException{
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {

            PreparedStatement statement = connection.prepareStatement("INSERT INTO TOURNAMENT (USERNAME,TURNTIME,PARTICIPANTS) VALUES(?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, username);
            statement.setInt(2, turnTime);
            statement.setInt(3,participants);

            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            int tournamentID;
            if (keys.next()) {
                tournamentID = keys.getInt(1);
                return tournamentID;
                // return new Match(Id, and all the other stuff)
            } else {
                throw new SQLException("No keys generated");
            }
        }
    }

    /**
     * Loads tournaments from the database
     * @return tournaments
     * @throws SQLException SQLException
     */
    @Override
    public ArrayList<Tournament> loadTournaments() throws SQLException {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select * from TOURNAMENT");
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Tournament> tournaments = new ArrayList<>();

            while (resultSet.next()) {
                String username = resultSet.getString("USERNAME");
                int id = resultSet.getInt("TOURNAMENTID");
                int turnTime = resultSet.getInt("TURNTIME");
                int participants = resultSet.getInt("PARTICIPANTS");
                Tournament tournament = new Tournament(username, turnTime, participants);
                tournament.setTournamentId(id);
                tournaments.add(tournament);
            }
            return tournaments;
        }
    }

    /**
     * Loads Tournaments for a user  in the  database
     * @param username username
     * @return tournaments
     * @throws SQLException SQLException
     */
    @Override
    public ArrayList<Tournament> loadTournamentsForASpecificUser(String username) throws SQLException {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select TOURNAMENT.TOURNAMENTID,TOURNAMENT.USERNAME,TURNTIME,PARTICIPANTS from TOURNAMENT join TOURNAMENT_PARTICIPATION TP on TOURNAMENT.TOURNAMENTID = TP.TOURNAMENTID where TP.USERNAME = ?;");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Tournament> tournaments = new ArrayList<>();

            while (resultSet.next()) {
                String creator = resultSet.getString("USERNAME");
                int id = resultSet.getInt("TOURNAMENTID");
                int turnTime = resultSet.getInt("TURNTIME");
                int participants = resultSet.getInt("PARTICIPANTS");
                Tournament tournament = new Tournament(creator, turnTime, participants);
                tournament.setTournamentId(id);
                tournaments.add(tournament);
            }
            return tournaments;
        }
    }

    /**
     * Updates the number of participants in the  database
     * @param ID id
     * @param newSize new size
     * @throws SQLException SQLException
     */
    @Override
    public void UpdateTournamentNrOfParticipants(int ID, int newSize) throws SQLException {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE TOURNAMENT SET PARTICIPANTS = ? WHERE TOURNAMENTID = ?");
            statement.setInt(1, newSize);
            statement.setInt(2, ID);
            statement.executeUpdate();
        }
    }

}
