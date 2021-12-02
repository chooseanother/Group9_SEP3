package persistence;

import model.Challenge;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * @author group9
 * @version 1.0
 */

public class ChallengeDB implements ChallengePersistence{

    /**
     * Creates a challenge in the database
     * @param challenge challenge
     * @throws SQLException sql exception
     */
    @Override
    public void createChallenge(Challenge challenge) throws SQLException {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO OPPONENT (USERNAME,USERNAME_OPPONENT,TURNTIME) VALUES(?, ?, ?)");
            statement.setString(1, challenge.getChallenger());
            statement.setString(2, challenge.getChallenged());
            statement.setInt(3, challenge.getTurnTime());
            statement.executeUpdate();
        }
    }

    /**
     * Loads challenges from the database
     * @return Challenges
     * @throws SQLException sql exception
     */
    public ArrayList<Challenge> loadChallenges() throws SQLException{
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select * from OPPONENT");
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Challenge> challenges = new ArrayList<>();

            while (resultSet.next()){
                String username = resultSet.getString("USERNAME");
                String opponent = resultSet.getString("USERNAME_OPPONENT");
                int turnTime = resultSet.getInt("TURNTIME");
                Challenge challenge = new Challenge(username,opponent, turnTime);
                challenges.add(challenge);
            }
            return challenges;
        }
    }

    /**
     * Loads challenges from the database by username
     * @param username username
     * @return challenge
     * @throws SQLException sql exception
     */
    public ArrayList<Challenge> loadChallenges(String username) throws SQLException{
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select * from OPPONENT where USERNAME_OPPONENT = ?");
            statement.setString(1,username);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Challenge> challenges = new ArrayList<>();

            while (resultSet.next()){
                String _username = resultSet.getString("USERNAME");
                String opponent = resultSet.getString("USERNAME_OPPONENT");
                int turnTime = resultSet.getInt("TURNTIME");
                Challenge challenge = new Challenge(_username,opponent, turnTime);
                challenges.add(challenge);
            }
            return challenges;
        }
    }

    /**
     * Deletes a challenge in the database
     * @param challenge challenge
     * @return if delete is successful
     * @throws SQLException sql exception
     */
    public boolean deleteChallenge(Challenge challenge) throws SQLException{
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("delete from OPPONENT where USERNAME = ? and USERNAME_OPPONENT = ?");
            statement.setString(1, challenge.getChallenger());
            statement.setString(2, challenge.getChallenged());
            statement.executeUpdate();
        }
        return true;
    }
}
