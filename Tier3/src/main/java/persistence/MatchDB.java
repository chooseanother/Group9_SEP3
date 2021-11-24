package persistence;

import model.Match;
import model.Move;
import model.Participant;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

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

    @Override public ArrayList<Match> getMatches(String username)
        throws SQLException
    {
        ArrayList<Match> matches = new ArrayList<>();
        try (Connection connection = ConnectionDB.getInstance().getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM MATCH JOIN MATCH_PARTICIPATION MP on MATCH.MATCHID = MP.MATCHID WHERE USERNAME = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                int matchId = resultSet.getInt("matchid");
                int tournamentid = resultSet.getInt("tournamentid");
                int turnTime = resultSet.getInt("turntime");
                String type = resultSet.getString("type");
                boolean finished = resultSet.getBoolean("finished");
                String usersTurn = resultSet.getString("usersturn");
                String latestMove = resultSet.getString("latestmove");
                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                Date date = null;
                try
                {
                    date = format.parse(latestMove);
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
                Match match = new Match(matchId, tournamentid, turnTime, type, finished ,usersTurn, date);
                matches.add(match);
            }
        }
        return matches;
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
