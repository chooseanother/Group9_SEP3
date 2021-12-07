package persistence;

import model.Challenge;
import model.Tournament;
import model.TournamentParticipation;
import model.Match;
import model.Move;
import model.User;
import model.*;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author group9
 * @version 1.0
 */

public class PersistenceDB implements Persistence{
    UserPersistence userDB;

    ChallengePersistence challengeDB;
    MatchPersistence matchDB;
    MatchParticipationPersistence matchParticipationDB;
    TournamentPersistence tournamentPersistence;
    MovePersistence movePersistence;
    TournamentParticipationPersistence tournamentParticipationPersistence;

    /**
     * Creates the persistence
     */
    public PersistenceDB(){
        userDB = new UserDB();
        challengeDB = new ChallengeDB();
        matchDB = new MatchDB();
        matchParticipationDB = new MatchParticipationDB();
        tournamentPersistence = new TournamentDB();
        movePersistence = new MoveDB();
        tournamentParticipationPersistence = new TournamentParticipationDb();
    }

    /**
     * Registers the user
     *
     * @param user user
     *
     */
    @Override
    public void registerUser(User user) throws SQLException {
        userDB.registerUser(user);
    }

    /**
     * Moves the piece
     *
     * @param matchId       match id
     * @param piece         piece
     * @param color         color
     * @param startPosition start position
     * @param endPosition   end position
     */
    @Override
    public void movePiece(int matchId, String piece, String color, String startPosition, String endPosition) throws SQLException {
        movePersistence.movePiece( matchId, piece, color, startPosition, endPosition);
    }

    /**
     * Upgrades the piece
     *
     * @param matchId       match id
     * @param piece         piece
     * @param color         color
     * @param startPosition start position
     * @param endPosition   end position
     */
    @Override
    public void upgradePiece(int matchId, String piece, String color, String startPosition, String endPosition) throws SQLException{
        movePersistence.upgradePiece(matchId,piece, color,startPosition, endPosition);
    }

    /**
     * Creates the match by turn time
     *
     * @param turnTime turn time
     * @return match
     */
    @Override
    public Match createMatch(int turnTime, String type) throws SQLException {
        return matchDB.createMatch(turnTime,type);
    }

    /**
     * Creates a tournament match
     *
     * @param turnTime     turn time
     * @return match
     */
    @Override
    public Match createMatch(int turnTime, String type, int tournamentId) throws SQLException {
        return matchDB.createMatch(turnTime, type ,tournamentId);
    }

    /**
     * Validates a challenge
     *
     * @param challenge challenge
     */
    @Override
    public void createChallenge(Challenge challenge) throws SQLException {
        challengeDB.createChallenge(challenge);
    }

    /**
     * Loads all the challenges
     *
     * @return all the challenges
     */
    @Override
    public ArrayList<Challenge> loadChallenges() throws SQLException {
        return challengeDB.loadChallenges();
    }

    /**
     * loads all the challenges of a user
     *
     * @param username username
     * @return all the challenges of a user
     */
    @Override
    public ArrayList<Challenge> loadChallenges(String username) throws SQLException {
        return challengeDB.loadChallenges(username);
    }

    /**
     * Rejects the challenge
     *
     * @param challenge challenge
     * @return if rejections is successful
     */
    @Override
    public boolean deleteChallenge(Challenge challenge) throws SQLException {
        return challengeDB.deleteChallenge(challenge);
    }

    /**
     * Updates the match user turn
     *
     * @param matchId match id
     * @param color   color
     */
    @Override
    public void updateMatchUserTurn(int matchId, String color) throws SQLException {
        matchDB.updateMatchUserTurn(matchId,color);
    }


    /**
     * Updates the outcome of a match
     *
     * @param matchId match id
     */
    @Override
    public void setMatchOutcome(int matchId, boolean finished) throws SQLException {
        matchDB.setMatchOutcome(matchId, finished);
    }

    /**
     * Returns all moves of a player
     *
     * @param matchID match id
     * @return moves
     */
    @Override
    public ArrayList<Move> getMoves(int matchID) throws SQLException {
        return movePersistence.getMoves(matchID);
    }

    /**
     * Returns all matches of a player
     *
     * @param username username
     * @return matches of a player
     */
    @Override public ArrayList<Match> getMatches(String username)
        throws SQLException
    {
        return matchDB.getMatches(username);
    }

    /**
     * Creates a participation for a match
     *
     * @param color    color
     * @param matchId  match id
     */
    @Override
    public void createMatchParticipation(String player, String color, int matchId) throws SQLException {
        matchParticipationDB.createMatchParticipation(player, color, matchId);
    }

    /**
     * Returns the tournament participants
     * @param tournamentId id
     * @return tournament participants
     */
    @Override
    public ArrayList<TournamentParticipation> loadTournamentParticipants(int tournamentId) throws SQLException {
        return tournamentParticipationPersistence.loadTournamentParticipants(tournamentId);
    }

    /**
     * Updates the participants placement
     *
     * @param username     username
     * @param placement    placement
     * @param tournamentId id
     */
    @Override
    public void updateParticipantsPlacement(String username, int placement, int tournamentId) throws SQLException {
        tournamentParticipationPersistence.updateParticipantsPlacement(username, placement, tournamentId);
    }


    /**
     * Returns the top players of a tournament
     *
     * @param tournamentID tournament id
     * @return top players of a tournament
     */
    @Override
    public ArrayList<TournamentParticipation> getTopPlayersInATournament(int tournamentID) throws SQLException {
        return tournamentParticipationPersistence.getTopPlayersInATournament(tournamentID);
    }

    /**
     * Updates the outcome of a match
     *
     * @param player  player
     * @param outcome outcome
     * @param matchId match id
     */
    @Override
    public void updateOutcome(String player, String outcome, int matchId) throws SQLException {
        matchParticipationDB.updateOutcome(player, outcome, matchId);
    }

    /**
     * Validates the login
     *
     * @param username username
     * @param password password
     * @return user
     */
    @Override public User validateLogin(String username, String password)
        throws SQLException
    {
        return userDB.validateLogin(username, password);
    }

    /**
     * Updates the user
     *
     * @param user user
     */
    @Override
    public void updateUser(User user) throws SQLException {
        userDB.updateUser(user);
    }

    /**
     * Returns user
     *
     * @param username username
     * @return user
     */
    @Override
    public User getUser(String username) throws SQLException {
        return userDB.getUser(username);
    }

    /**
     * Returns the participants by match id
     *
     * @param matchId match id
     * @return participants by match id
     */
    @Override public ArrayList<Participant> getParticipants(int matchId)
        throws SQLException
    {
        return matchParticipationDB.getParticipants(matchId);
    }

    /**
     * Returns the match
     *
     * @param matchId match id
     * @return match
     */
    @Override
    public Match getMatch(int matchId) throws SQLException {
        return matchDB.getMatch(matchId);
    }

    /**
     * Join a tournament
     *
     * @param username     username
     * @param tournamentID tournament id
     * @return id
     */
    @Override
    public int createTournamentParticipation(String username, int tournamentID) throws SQLException {
        return tournamentParticipationPersistence.createTournamentParticipation(username, tournamentID);
    }

    /**
     * Validates the tournament
     *
     * @return tournament id
     */
    @Override
    public int createTournament(String username, int turnTime, int participants) throws SQLException {
        return tournamentPersistence.createTournament(username, turnTime, participants);
    }

    /**
     * Returns a tournament
     *
     * @return tournament
     */
    @Override
    public ArrayList<Tournament> loadTournaments() throws SQLException {
        return tournamentPersistence.loadTournaments();
    }

    /**
     * Updates the nr of tournament participants
     *
     * @param ID      id
     * @param newSize new size
     */
    @Override
    public void updateTournamentNrOfParticipants(int ID, int newSize) throws SQLException {
        tournamentPersistence.updateTournamentNrOfParticipants(ID, newSize);
    }

    /**
     * Returns all tournaments for a user
     *
     * @param username username
     * @return tournaments for a user
     */
    @Override
    public ArrayList<Tournament> loadTournamentsForASpecificUser(String username) throws SQLException {
        return tournamentPersistence.loadTournamentsForASpecificUser(username);
    }

    /**
     * Sets the tournament outcome
     * @param tournamentId id
     * @param finished status
     * @throws SQLException SQLException
     */
    @Override
    public void setTournamentOutcome(int tournamentId, boolean finished) throws SQLException {
        tournamentPersistence.setTournamentOutcome(tournamentId, finished);
    }

    /**
     * Increments the win loss or draw attributes
     *
     * @param username username
     * @param type     type
     */
    @Override
    public void incrementWinLossDraw(String username, String type) throws SQLException {
        userDB.incrementWinLossDraw(username, type);
    }
}
