package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MatchDb implements MatchPersistence{

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
