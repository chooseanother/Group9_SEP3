package model;

import java.util.ArrayList;

public interface Model {
    String registerUser(String username, String password, String email);

    ChessPiece moveChessPiece(ChessPiece selected, int matchID, String username);

    ChessPiece upgradeChessPiece(String upgradeSelected, ChessPiece toUpgrade, int matchID, String username);

    ChessBoard getChessBoard(int matchID);

    String validateChallenge(Challenge challenge);

    User validateLogin(String username, String password);

    User updateUser(User user);

    ArrayList<Challenge> loadChallenges();

    ArrayList<Challenge> loadChallenges(String username);

    boolean acceptChallenge(Challenge challenge);

    boolean rejectChallenge(Challenge challenge);

    int getMatchScores(boolean black, int matchID);

    ArrayList<ChessPiece> getRemovedChessPieces(int matchID);

    int createTournament(Tournament tournament);

    boolean joinATournament(String username, int tournamentID);

    ArrayList<Match> getMatches(String username);

    void updateOutcome(String player, String outcome, int matchId);

    ArrayList<Match> getMatchHistory(String username);

    Match getMatch(int matchId);

    Match addParticipantsToMatch(Match match);

    Match checkTurnTime(Match match);

    ArrayList<Move> getMoves(int matchId);

    ArrayList<Tournament> getAllTournamentsWhereAUserHasBeen(String username);

    void loadTop3PlayersInTournament(ArrayList<Tournament> tournamentsToLoad);

    void sendMail(int matchId, String username);

    ArrayList<Participant> getParticipants(int matchId);
}
