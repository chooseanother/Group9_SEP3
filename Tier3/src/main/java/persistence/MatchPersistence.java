package persistence;

import model.Match;
import model.Move;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MatchPersistence {
    void movePiece(int matchId, String piece, String color, String startPosition, String endPosition) throws SQLException;

    void upgradePiece(int matchId, String piece, String color, String startPosition, String endPosition) throws SQLException;

    Match createMatch(int turnTime, String type) throws SQLException;

    Match createMatch(int turnTime, String type, int tournamentId) throws SQLException;

    void updateMatchUserTurn(int matchId, String color) throws SQLException;

    void setMatchOutcome(int matchId, boolean finished) throws SQLException;

    ArrayList<Move> getMoves(int matchID) throws SQLException;

    Match getMatch(int matchId) throws SQLException;

    ArrayList<Match> getMatches(String username) throws SQLException;
}
