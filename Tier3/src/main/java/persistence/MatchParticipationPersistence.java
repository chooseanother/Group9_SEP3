package persistence;

import model.Participant;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MatchParticipationPersistence {
    int createMatchParticipation(String player, String color, int matchId) throws SQLException;

    ArrayList<Participant> getParticipants(int matchId) throws SQLException;

}
