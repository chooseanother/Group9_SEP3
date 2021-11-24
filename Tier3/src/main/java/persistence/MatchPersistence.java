package persistence;

import model.Match;
import model.Move;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public interface MatchPersistence {
    void MovePiece( int matchId, String piece, String color, String startPosition, String endPosition) throws SQLException;

    void UpgradePiece(int matchId, String piece, String color, String startPosition, String endPosition) throws SQLException;
  
    int createMatch(int turnTime, String type) throws SQLException;

   void UpdateMatchUserTurn(int matchId,String color) throws SQLException;

    ArrayList<Move> getMoves(int matchID) throws SQLException;

    ArrayList<Match> getMatches(String username)
        throws SQLException;
}
