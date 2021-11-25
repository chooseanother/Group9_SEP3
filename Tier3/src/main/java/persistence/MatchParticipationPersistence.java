package persistence;

import java.sql.SQLException;

public interface MatchParticipationPersistence {
    void createMatchParticipation(String player, String color, int matchId) throws SQLException;
    void updateOutcome(String player, String outcome, int matchId) throws SQLException;
    String getParticipationColor(String player, int matchId) throws SQLException;
}
