package model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface Model {
    String registerUser(String username, String password, String email);
    ChessPiece MoveChessPiece(ChessPiece selected);
    ChessPiece UpgradeChessPiece(String upgradeSelected,ChessPiece toUpgrade);
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
    void updateOutcome(String player, String outcome, int matchId);
    String getParticipationColor(String player, int matchId);
    ArrayList<Match> getMatchHistory(String username);
    Match getMatch(int matchId);
    Match addParticipantsToMatch(Match match);
    Match checkTurnTime(Match match);
    ArrayList<Move> getMoves(int matchId);
}
