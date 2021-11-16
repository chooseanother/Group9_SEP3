package persistence;

import model.User;

import java.sql.SQLException;

public class PersistenceDB implements Persistence{
    UserPersistence userDB;
    MatchPersistence MatchDb;

    public PersistenceDB(){
        userDB = new UserDB();
        MatchDb = new MatchDb();
    }

    @Override
    public void registerUser(User user) throws SQLException {
        userDB.registerUser(user);
    }

    @Override
    public void MovePiece(int moveId, int matchId, String piece, String color, String startPosition, String endPosition) throws SQLException {
        MatchDb.MovePiece(moveId, matchId, piece, color, startPosition, endPosition);
    }
}
