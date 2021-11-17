package persistence;

import java.sql.SQLException;

public interface MatchPersistence {
    int createMatch(int turnTime, String type) throws SQLException;
}
