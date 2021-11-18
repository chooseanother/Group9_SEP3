package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchDB implements MatchPersistence{
    @Override
    public int createMatch(int turnTime, String type) throws SQLException {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO MATCH (TURNTIME,TYPE) VALUES(?, ?)",PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, turnTime);
            statement.setString(2, type);
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            int matchId;
            if (keys.next()){
                matchId = keys.getInt(1);
                return matchId;
                // return new Match(Id, and all the other stuff)
            } else {
                throw new SQLException("No keys generated");
            }


        }

    }
}
