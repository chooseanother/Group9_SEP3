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
            statement.setString(1, user.getUsername());
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
    }

    @Override
    public void updateUser(User user) throws SQLException {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE _User SET(password, email) = ( ?, ?) WHERE username = ?");
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getUsername());
            statement.executeUpdate();
        }
    }

    @Override
    public User getUser(String username) throws SQLException {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT  * from _User WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                int scorePoints = resultSet.getInt("scorePoints");
                int wins = resultSet.getInt("wins");
                int losses = resultSet.getInt("losses");
                int draws = resultSet.getInt("draws");
                int gamesPlayed = resultSet.getInt("gamesPlayed");
                User user = new User(username, password, email, scorePoints,
                        wins, losses, draws, gamesPlayed);
                return user;
            } else {
                throw new IllegalArgumentException("User doesn't exist");
            }
        }
    }

    @Override
    public void incrementWinLossDraw(String username, String type) throws SQLException {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "update _user set ? = ? + 1, gamesplayed = gamesplayed + 1 where username = ?");
            statement.setString(1, type);
            statement.setString(2, type);
            statement.setString(3, username);
            statement.executeUpdate();
        }

    }
}
