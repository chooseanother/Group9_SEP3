package persistence;

import model.Match;
import model.Move;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public interface MatchPersistence {
    void MovePiece( int matchId, String piece, String color, String startPosition, String endPosition) throws SQLException;

    void UpgradePiece(int matchId, String piece, String color, String startPosition, String endPosition) throws SQLException;
  
    Match createMatch(int turnTime, String type) throws SQLException;

    Match createMatch(int turnTime, String type, int tournamentId) throws SQLException;

   void UpdateMatchUserTurn(int matchId,String color) throws SQLException;

    void setMatchOutcome(int matchId, boolean finished) throws SQLException;

    ArrayList<Move> getMoves(int matchID) throws SQLException;

    ArrayList<Match> getMatches(String username)
        throws SQLException;
}
