package persistence;

import java.sql.SQLException;

public interface MatchParticipationPersistence {
    int createMatchParticipation(String player, String color, int matchId) throws SQLException;
}
