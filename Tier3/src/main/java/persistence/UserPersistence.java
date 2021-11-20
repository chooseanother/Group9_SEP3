package persistence;

import model.User;

import java.sql.SQLException;

public interface UserPersistence {
    void registerUser(User user) throws SQLException;

    User validateLogin(String username, String password) throws SQLException;

    void updateUser(User user) throws SQLException;

    User getUser(String username) throws SQLException;
}
