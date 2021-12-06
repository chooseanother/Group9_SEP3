package persistence;

import model.Match;
import model.Move;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MatchPersistence {

    Match createMatch(int turnTime, String type) throws SQLException;

    Match createMatch(int turnTime, String type, int tournamentId) throws SQLException;

    void updateMatchUserTurn(int matchId, String color) throws SQLException;

    void setMatchOutcome(int matchId, boolean finished) throws SQLException;

    Match getMatch(int matchId) throws SQLException;

    ArrayList<Match> getMatches(String username) throws SQLException;
}
