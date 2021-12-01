package model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface Model {
    String registerUser(String username, String password, String email);
    ChessPiece MoveChessPiece(ChessPiece selected, int matchID,String username);
    ChessPiece UpgradeChessPiece(String upgradeSelected,ChessPiece toUpgrade,int matchID,String username);
    ChessBoard getChessBoard(int matchID);
    String validateChallenge(Challenge challenge);
    User validateLogin(String userName, String password);
    User updateUser(User user);
    ArrayList<Challenge> loadChallenges();
    ArrayList<Challenge> loadChallenges(String username);
    boolean acceptChallenge(Challenge challenge);
    boolean rejectChallenge(Challenge challenge);
    public int getMatchScores(boolean Black, int matchID);
    public ArrayList<ChessPiece> getRemovedChessPieces(int matchID);
    int CreateTournament(Tournament tournament);
    boolean joinATournament(String username, int tournamentID, int placement);
    ArrayList<Match> getMatches(String username);
    void updateOutcome(String player, String outcome, int matchId);
    String getParticipationColor(String player, int matchId);
    ArrayList<Match> getMatchHistory(String username);
    Match getMatch(int matchId);
    Match addParticipantsToMatch(Match match);
    Match checkTurnTime(Match match);
    ArrayList<Move> getMoves(int matchId);
    ArrayList<Tournament> getAllTournamentsWhereAUserHasBeen(String username);
    void loadTop3PlayersInTournament(ArrayList<Tournament> tournamentsToLoad);
    void sendMail(int matchId, String username);
}
