package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MatchParticipationDB implements MatchParticipationPersistence{
    @Override
    public int createMatchParticipation(String player, String color, int matchId) throws SQLException {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement2 = connection.prepareStatement("INSERT INTO MATCH_PARTICIPATION (USERNAME, MATCHID, COLOR) VALUES (?,?,?)");
            statement2.setString(1, player);
            statement2.setInt(2, matchId);
            statement2.setString(3, color);
            statement2.executeUpdate();
        }
        return 1;
    }
}
