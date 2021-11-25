package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchParticipationDB implements MatchParticipationPersistence{
    @Override
    public void createMatchParticipation(String player, String color, int matchId) throws SQLException {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement2 = connection.prepareStatement("INSERT INTO MATCH_PARTICIPATION (USERNAME, MATCHID, COLOR) VALUES (?,?,?)");
            statement2.setString(1, player);
            statement2.setInt(2, matchId);
            statement2.setString(3, color);
            statement2.executeUpdate();
        }
    }

    @Override
    public void updateOutcome(String player, String outcome, int matchId) throws SQLException {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement2 = connection.prepareStatement("UPDATE MATCH_PARTICIPATION SET OUTCOME  = ? WHERE USERNAME = ? AND MATCHID = ?");
            statement2.setString(1, outcome);
            statement2.setString(2, player);
            statement2.setInt(3, matchId);
            statement2.executeUpdate();
        }
    }

    @Override
    public String getParticipationColor(String player, int matchId) throws SQLException {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT COLOR from MATCH_PARTICIPATION WHERE username = ? AND MATCHID = ?");
            statement.setString(1, player);
            statement.setInt(2, matchId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getString("color");
            }
        }
        return null;
    }
}
