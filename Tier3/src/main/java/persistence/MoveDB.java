package persistence;

import model.Move;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MoveDB implements MovePersistence{
    /**
     * Moves the piece in the database
     * @param matchId match id
     * @param piece piece
     * @param color color
     * @param startPosition start position
     * @param endPosition end position
     * @throws SQLException SQLException
     */
    @Override
    public void movePiece(int matchId, String piece, String color, String startPosition, String endPosition) throws SQLException {
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

    /**
     * Upgrade piece in the database
     * @param matchId match id
     * @param piece piece
     * @param color color
     * @param startPosition start position
     * @param endPosition end position
     * @throws SQLException SQLException
     */
    @Override
    public void upgradePiece(int matchId, String piece, String color, String startPosition, String endPosition) throws SQLException {
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

    /**
     * Returns the moves from the database
     * @param matchID match id
     * @return moves
     * @throws SQLException SQLException
     */
    @Override
    public ArrayList<Move> getMoves(int matchID) throws SQLException {
        ArrayList<Move> moves = new ArrayList<>();
        try (Connection connection = ConnectionDB.getInstance().getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * from MOVE WHERE MATCHID = ? ORDER BY MOVEID");
            statement.setInt(1, matchID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                int moveId = resultSet.getInt("moveid");
                int localMatchId = resultSet.getInt("matchid");
                String piece = resultSet.getString("piece");
                String color = resultSet.getString("color");
                String startPosition = resultSet.getString("startposition");
                String endPosition = resultSet.getString("endposition");

                Move move = new Move(moveId,localMatchId,piece,color,startPosition,endPosition);
                moves.add(move);
            }
        }
        return moves;
    }
}
