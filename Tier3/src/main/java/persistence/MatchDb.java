package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MatchDb implements MatchPersistence{

    @Override
    public void MovePiece(int moveId, int matchId, String piece, String color, String startPosition, String endPosition) throws SQLException {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO MOVE (MOVEID, MATCHID, PIECE, COLOR, STARTPOSITION, ENDPOSITION) " +
                    "VALUES(?, ?, ?, ?, ?, ?)");
            statement.setInt(1, moveId);
            statement.setInt(2,matchId);
            statement.setString(3,piece);
            statement.setString(4,color);
            statement.setString(5, startPosition);
            statement.setString(6,endPosition);
            statement.executeUpdate();
        }
    }
}
