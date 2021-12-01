package persistence;

import model.Match;
import model.Move;
import model.Participant;
import model.User;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author group9
 * @version 1.0
 */

public class MatchDB implements MatchPersistence{

    /**
     * Creates match in the database
     * @param turnTime turn time
     * @param type type
     * @return match
     * @throws SQLException SQLException
     */
    @Override
    public Match createMatch(int turnTime, String type) throws SQLException {
        // should set latest move to current time
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO MATCH (TURNTIME,TYPE,LATESTMOVE) VALUES(?, ?, ?)",PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, turnTime);
            statement.setString(2, type);
            Timestamp now = java.sql.Timestamp.valueOf(LocalDateTime.now());
            statement.setTimestamp(3,now);
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();

            if (keys.next()){
               return new Match(keys.getInt(1),0,turnTime,type,false,"White",now.getTime());
            } else {
                throw new SQLException("No keys generated");
            }
        }
    }

    /**
     * Creates tournament match in the database
     * @param turnTime turn time
     * @param type type
     * @param tournamentId id
     * @return match
     * @throws SQLException SQLException
     */
    @Override
    public Match createMatch(int turnTime, String type, int tournamentId) throws SQLException {
        // should set latest move to current time
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO MATCH (TURNTIME,TYPE,TOURNAMENTID,LATESTMOVE) VALUES(?, ?, ?, ?)",PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, turnTime);
            statement.setString(2, type);
            statement.setInt(3,tournamentId);
            Timestamp now = java.sql.Timestamp.valueOf(LocalDateTime.now());
            statement.setTimestamp(4,now);
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()){
                return new Match(keys.getInt(1),tournamentId,turnTime,type,false,"White",now.getTime());
            } else {
                throw new SQLException("No keys generated");
            }
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

    /**
     * Returns the matches from the database
     * @param username username
     * @return matches
     * @throws SQLException SQLException
     */
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
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = null;
                try
                {
                    date = format.parse(latestMove);
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
                Match match = new Match(matchId, tournamentid, turnTime, type, finished ,usersTurn, date.getTime());
                matches.add(match);
            }
        }
        return matches;
    }

    /**
     * Updates the match user turn in the database
     * @param matchId match id
     * @param color color
     * @throws SQLException SQLException
     */
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

    /**
     * Sets the match outcome
     * @param matchId match id
     * @param finished finished or not
     * @throws SQLException SQLException
     */
    @Override
    public void setMatchOutcome(int matchId, boolean finished) throws SQLException {
        try(Connection connection = ConnectionDB.getInstance().getConnection()){
            PreparedStatement statement = connection.prepareStatement("UPDATE MATCH SET FINISHED = ? WHERE MATCHID = ?");
            statement.setBoolean(1, finished);
            statement.setInt(2,matchId);
            statement.executeUpdate();
        }
    }

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

    /** Returns the match id from the database
     * @param matchId id
     * @return match id
     * @throws SQLException SQLException
     */
    @Override
    public Match getMatch(int matchId) throws SQLException {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM MATCH WHERE MATCHID = ?");
            statement.setInt(1, matchId);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                int localMatchId = resultSet.getInt("matchid");
                int tournamentId = resultSet.getInt("tournamentid");
                int turnTime = resultSet.getInt("turntime");
                String type = resultSet.getString("type");
                boolean finished = resultSet.getBoolean("finished");
                String usersTurn = resultSet.getString("usersturn");
                String latestMove = resultSet.getString("latestmove");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = null;
                try {
                    date = format.parse(latestMove);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return new Match(localMatchId, tournamentId, turnTime, type, finished, usersTurn, date.getTime());
            }
        }
        return null;
    }
}
