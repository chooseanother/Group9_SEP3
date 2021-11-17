package persistence;

import model.Challenge;
import model.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class PersistenceDB implements Persistence{
    UserPersistence userDB;
    ChallengePersistence challengeDB;
    MatchPersistence matchDB;
    MatchParticipationPersistence matchParticipationDB;

    public PersistenceDB(){
        userDB = new UserDB();
        challengeDB = new ChallengeDB();
        matchDB = new MatchDB();
        matchParticipationDB = new MatchParticipationDB();
    }

    @Override
    public void registerUser(User user) throws SQLException {
        userDB.registerUser(user);
    }

    @Override
    public void createChallenge(Challenge challenge) throws SQLException {
        challengeDB.createChallenge(challenge);
    }

    @Override
    public ArrayList<Challenge> loadChallenges() throws SQLException {
        return challengeDB.loadChallenges();
    }

    @Override
    public ArrayList<Challenge> loadChallenges(String username) throws SQLException {
        return challengeDB.loadChallenges(username);
    }

    @Override
    public boolean deleteChallenge(Challenge challenge) throws SQLException {
        return challengeDB.deleteChallenge(challenge);
    }

    @Override
    public int createMatch(int turnTime, String type) throws SQLException {
        return matchDB.createMatch(turnTime, type);
    }

    @Override
    public int createMatchParticipation(String player, String color, int matchId) throws SQLException {
        return matchParticipationDB.createMatchParticipation(player, color, matchId);
    }
}
