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

    @Override
    public void MovePiece( int matchId, String piece, String color, String startPosition, String endPosition) throws SQLException {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO MOVE ( MATCHID, PIECE, COLOR, STARTPOSITION, ENDPOSITION) " +
                    "VALUES(?, ?, ?, ?, ?)");
            statement.setInt(1,matchId);
            statement.setString(2,piece);
            statement.setString(3,color);
            statement.setString(4, startPosition);
            statement.setString(5,endPosition);
            statement.executeUpdate();
        }
    }

    @Override
    public void UpgradePiece(int matchId, String piece, String color, String startPosition, String endPosition) throws SQLException {
        try(Connection connection = ConnectionDB.getInstance().getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO MOVE ( MATCHID, PIECE, COLOR, STARTPOSITION, ENDPOSITION) " +
                    "VALUES(?, ?, ?, ?, ?)");
            statement.setInt(1,matchId);
            statement.setString(2,piece);
            statement.setString(3,color);
            statement.setString(4, startPosition);
            statement.setString(5,endPosition);
            statement.executeUpdate();
        }
    }
}
