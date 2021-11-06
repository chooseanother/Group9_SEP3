package persistence;

import model.User;

import java.sql.SQLException;

public class PersistenceDB implements Persistence{
    UserPersistence userDB;

    public PersistenceDB(){
        userDB = new UserDB();
    }

    @Override
    public void registerUser(User user) throws SQLException {
        userDB.registerUser(user);
    }
}
