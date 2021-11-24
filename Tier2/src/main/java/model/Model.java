package model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface Model {
    String registerUser(String username, String password, String email);
    ChessPiece MoveChessPiece(int firstLayer, int secondLayer);
    ChessPiece UpgradeChessPiece(String upgradeSelected);
    ChessBoard getChessBoard();
    String validateChallenge(Challenge challenge);
    User validateLogin(String userName, String password);
    User updateUser(User user);
    ArrayList<Challenge> loadChallenges();
    ArrayList<Challenge> loadChallenges(String username);
    boolean acceptChallenge(Challenge challenge);
    boolean rejectChallenge(Challenge challenge);
    ArrayList<ChessPiece> getRemovedChessPieces();
    int getMatchScores(boolean Black);
    ArrayList<Match> getMatches(String username);
}
