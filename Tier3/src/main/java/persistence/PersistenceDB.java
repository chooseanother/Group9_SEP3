package persistence;

import model.*;

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

    public void MovePiece( int matchId, String piece, String color, String startPosition, String endPosition) throws SQLException {
        matchDB.MovePiece( matchId, piece, color, startPosition, endPosition);
    }

    @Override
    public void UpgradePiece(int matchId, String piece, String color, String startPosition, String endPosition) throws SQLException{
        matchDB.UpgradePiece(matchId,piece, color,startPosition, endPosition);
    }

    @Override
    public Match createMatch(int turnTime, String type) throws SQLException {
        return matchDB.createMatch(turnTime,type);
    }

    @Override
    public Match createMatch(int turnTime, String type, int tournamentId) throws SQLException {
        return matchDB.createMatch(turnTime,toString(),tournamentId);
    }

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
    public void UpdateMatchUserTurn(int matchId, String color) throws SQLException {
        matchDB.UpdateMatchUserTurn(matchId,color);
    }

    @Override
    public ArrayList<Move> getMoves(int matchID) throws SQLException {
        return matchDB.getMoves(matchID);
    }

    @Override public ArrayList<Match> getMatches(String username)
        throws SQLException
    {
        return matchDB.getMatches(username);
    }

    @Override
    public void createMatchParticipation(String player, String color, int matchId) throws SQLException {
        matchParticipationDB.createMatchParticipation(player, color, matchId);
    }

    @Override
    public void updateOutcome(String player, String outcome, int matchId) throws SQLException {
        matchParticipationDB.updateOutcome(player, outcome, matchId);
    }

    @Override
    public String getParticipationColor(String player, int matchId) throws SQLException {
        return matchParticipationDB.getParticipationColor(player,matchId);
    }

    @Override public User validateLogin(String username, String password)
        throws SQLException
    {
        return userDB.validateLogin(username, password);
    }

    @Override
    public void updateUser(User user) throws SQLException {
        userDB.updateUser(user);
    }

    @Override
    public User getUser(String username) throws SQLException {
        return userDB.getUser(username);
    }

    @Override public ArrayList<Participant> getParticipants(int matchId)
        throws SQLException
    {
        return matchParticipationDB.getParticipants(matchId);
    }
}
