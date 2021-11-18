package persistence;

import model.Challenge;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChallengeDB implements ChallengePersistence{
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
