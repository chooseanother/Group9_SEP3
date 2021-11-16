package persistence;

import java.sql.SQLException;

public interface MatchPersistence {
    void MovePiece( int matchId, String piece, String color, String startPosition, String endPosition) throws SQLException;

    void UpgradePiece(String upgradeSelected) throws SQLException;

}
