package persistence;

import model.Move;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MovePersistence {
    void movePiece(int matchId, String piece, String color, String startPosition, String endPosition) throws SQLException;

    void upgradePiece(int matchId, String piece, String color, String startPosition, String endPosition) throws SQLException;
    ArrayList<Move> getMoves(int matchID) throws SQLException;
}
