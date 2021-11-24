package persistence;

import model.Match;
import model.Move;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class MatchDB implements MatchPersistence{
    @Override
    public Match createMatch(int turnTime, String type) throws SQLException {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO MATCH (TURNTIME,TYPE) VALUES(?, ?)",PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, turnTime);
            statement.setString(2, type);
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();

            if (keys.next()){
               return new Match(keys.getInt(1),0,turnTime,type,false,"White",null);
            } else {
                throw new SQLException("No keys generated");
            }
        }
    }

    @Override
    public Match createMatch(int turnTime, String type, int tournamentId) throws SQLException {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO MATCH (TURNTIME,TYPE,TOURNAMENTID) VALUES(?, ?, ?)",PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, turnTime);
            statement.setString(2, type);
            statement.setInt(3,tournamentId);
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()){
                return new Match(keys.getInt(1),tournamentId,turnTime,type,false,"White",null);
            } else {
                throw new SQLException("No keys generated");
            }
        }
    }

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

    @Override
    public void UpdateMatchUserTurn(int matchId, String color) throws SQLException {
        try(Connection connection = ConnectionDB.getInstance().getConnection()){
            PreparedStatement statement = connection.prepareStatement("UPDATE MATCH SET USERSTURN = ?, LATESTMOVE = ? WHERE MATCHID = ?");
            statement.setString(1,color);
            statement.setTimestamp(2,java.sql.Timestamp.valueOf(LocalDateTime.now()));
            statement.setInt(3,matchId);
            statement.executeUpdate();
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
