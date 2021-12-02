package persistence;

import model.Participant;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MatchParticipationPersistence {
    ArrayList<Participant> getParticipants(int matchId) throws SQLException;
    void createMatchParticipation(String player, String color, int matchId) throws SQLException;
    void updateOutcome(String player, String outcome, int matchId) throws SQLException;
}
