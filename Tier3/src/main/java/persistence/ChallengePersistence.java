package persistence;

import model.Challenge;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ChallengePersistence {
    void createChallenge(Challenge challenge) throws SQLException;
    ArrayList<Challenge> loadChallenges() throws SQLException;
    ArrayList<Challenge> loadChallenges(String username) throws SQLException;
    boolean deleteChallenge(Challenge challenge) throws SQLException;
}
