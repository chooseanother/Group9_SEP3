package persistence;

import model.Move;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MatchPersistence {
    void MovePiece( int matchId, String piece, String color, String startPosition, String endPosition) throws SQLException;

    void UpgradePiece(int matchId, String piece, String color, String startPosition, String endPosition) throws SQLException;
  
    int createMatch(int turnTime, String type) throws SQLException;

    ArrayList<Move> getMoves(int matchID) throws SQLException;
}
