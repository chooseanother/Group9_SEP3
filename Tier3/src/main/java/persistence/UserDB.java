package persistence;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB implements UserPersistence {
    @Override
    public void registerUser(User user) throws SQLException{
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO _User (username, password, email) VALUES(?, ?, ?)");
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.executeUpdate();
        }
    }

    @Override public User validateLogin(String username, String password)
        throws SQLException
    {
        try (Connection connection = ConnectionDB.getInstance().getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT  * from _User WHERE username = ? AND password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
            {
                String email = resultSet.getString("email");
                int scorePoints = resultSet.getInt("scorePoints");
                int wins = resultSet.getInt("wins");
                int losses = resultSet.getInt("losses");
                int draws = resultSet.getInt("draws");
                int gamesPlayed = resultSet.getInt("gamesPlayed");
                User user = new User(username, password, email, scorePoints,
                    wins, losses, draws, gamesPlayed);
                return user;
            }
            else
            {
                throw new IllegalArgumentException("User doesn't exist");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
