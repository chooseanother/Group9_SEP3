package RMI;

import model.Challenge;
import model.Tournament;
import model.TournamentParticipation;
import model.Match;
import model.Move;
import model.User;

import model.*;
import persistence.Persistence;
import persistence.PersistenceDB;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author group9
 * @version 1.0
 */

public class Tier3RMIServerController
        extends UnicastRemoteObject
        implements ITier3RMIServer {
    private Persistence persistence;

    /**
     * Creates the tier 3 rmi controller
     * @throws RemoteException Remote Exception
     */
    public Tier3RMIServerController()
            throws RemoteException {
        try {
            startRegistry();
            startServer();
			System.out.println("Server started...");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        persistence = new PersistenceDB();
    }

    /**
     * Starts the registry
     * @throws RemoteException Remote exception
     */
    private void startRegistry() throws RemoteException {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            System.out.println("Registry started");
        } catch (ExportException e) {
            System.err.println("Server exception: " + e);
            e.printStackTrace();
        }
    }

    /**
     * Starts the server
     * @throws MalformedURLException
     * @throws RemoteException Remote exception
     */
    private void startServer() throws MalformedURLException, RemoteException {
        Naming.rebind(T3_SERVICE_NAME, this);
    }

    /**
     * Registers the user
     * @param user user
     * @return if the registration is successful
     * @throws RemoteException Remote exception
     */
    @Override
    public boolean registerUser(User user) throws RemoteException {
        try {
            persistence.registerUser(user);
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Moves the piece
     * @param matchId match id
     * @param piece piece
     * @param color color
     * @param startPosition start position
     * @param endPosition end position
     * @return if the move is successful
     */
    @Override
    public boolean movePiece(int matchId, String piece, String color, String startPosition, String endPosition){
        try {
            persistence.movePiece( matchId, piece, color, startPosition, endPosition);
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Validates a challenge
     * @param challenge challenge
     * @return if the validation is successful
     * @throws RemoteException Remote exception
     */
    @Override
    public boolean validateChallenge(Challenge challenge) throws RemoteException {
        try {
            persistence.createChallenge(challenge);
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Upgrades the piece
     * @param matchId match id
     * @param piece piece
     * @param color color
     * @param startPosition start position
     * @param endPosition end position
     * @return if the upgrade is successful
     * @throws RemoteException Remote exception
     */
    @Override
    public boolean upgradePiece(int matchId, String piece, String color, String startPosition, String endPosition) throws RemoteException {
        try {
            persistence.upgradePiece( matchId,  piece,  color,  startPosition, endPosition);
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns all of moves of a player
     * @param matchID match id
     * @return moves
     * @throws RemoteException Remote exception
     */
    @Override
    public ArrayList<Move> getMoves(int matchID) throws RemoteException {
        try {
            return persistence.getMoves(matchID);
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns all matches of a player
     * @param username username
     * @return matches of a player
     * @throws RemoteException Remote exception
     */
    @Override
    public ArrayList<Match> getMatches(String username)
        throws RemoteException
    {
        try
        {
            return persistence.getMatches(username);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns the participants by match id
     * @param matchId match id
     * @return participants by match id
     * @throws RemoteException Remote exception
     */
    @Override
    public ArrayList<Participant> getParticipants(int matchId)
        throws RemoteException
    {
        try
        {
            return persistence.getParticipants(matchId);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updates the outcome of a match
     * @param player player
     * @param outcome outcome
     * @param matchId match id
     * @throws RemoteException Remote exception
     */
    @Override
    public void updateOutcome(String player, String outcome, int matchId) throws RemoteException {
        try {
            persistence.updateOutcome(player, outcome, matchId);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Sets the match outcome
     * @param matchId match id
     * @param finished status
     * @return if the outcome is successful
     * @throws RemoteException Remote exception
     */
    @Override
    public boolean setMatchOutcome(int matchId, boolean finished) throws RemoteException {
        try {
            persistence.setMatchOutcome(matchId, finished);
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Loads all the challenges
     * @return all the challenges
     * @throws RemoteException Remote exception
     */
    @Override
    public ArrayList<Challenge> loadChallenges() throws RemoteException {
        try {
            ArrayList<Challenge> challenges = persistence.loadChallenges();
            return challenges;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * loads all the challenges of a user
     * @param username username
     * @return all the challenges of a user
     * @throws RemoteException Remote exception
     */
    @Override
    public ArrayList<Challenge> loadChallenges(String username) throws RemoteException {
        try {
            ArrayList<Challenge> challenges = persistence.loadChallenges(username);
            return challenges;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Rejects the challenge
     * @param challenge challenge
     * @return if rejections is successful
     * @throws RemoteException Remote exception
     */
    @Override
    public boolean rejectChallenge(Challenge challenge) throws RemoteException {
        try {
            return persistence.deleteChallenge(challenge);
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Validates the login
     * @param username username
     * @param password password
     * @return user
     * @throws RemoteException Remote exception
     */
    @Override public User validateLogin(String username, String password)
        throws RemoteException
    {
        try{
            User user = persistence.validateLogin(username, password);
            return user;
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns the match
     * @param matchId match id
     * @return match
     * @throws RemoteException Remote exception
     */
    @Override
    public Match getMatch(int matchId) throws RemoteException {
        try{
            return persistence.getMatch(matchId);
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updates the match user turn
     * @param matchID match id
     * @param color color
     * @return if update is successful
     * @throws RemoteException Remote exception
     */
    @Override
    public boolean updateMatchUsersTurn(int matchID, String color) throws RemoteException{
        try{
            persistence.updateMatchUserTurn(matchID,color);
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    /**
     * Creates the match by turn time
     * @param turnTime turn time
     * @return match
     * @throws RemoteException Remote exception
     */
    @Override
    public Match createMatch(int turnTime) throws RemoteException {
        try {
            return persistence.createMatch(turnTime, "Friendly");
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a tournament match
     * @param turnTime turn time
     * @param tournamentID match id
     * @return match
     * @throws RemoteException Remote exception
     */
    @Override
    public Match createMatch(int turnTime, int tournamentID) throws RemoteException {
        try {
            return persistence.createMatch(turnTime, "Friendly", tournamentID);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a participation for a match
     * @param username username
     * @param color color
     * @param matchId match id
     * @return if creation is successful
     * @throws RemoteException Remote exception
     */
    @Override
    public boolean createParticipation(String username, String color, int matchId) throws RemoteException {
        try{
            persistence.createMatchParticipation(username,color,matchId);
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Removes the challenge
     * @param challenge challenge
     * @return if removal is successful
     * @throws RemoteException Remote exception
     */
    @Override
    public boolean removeChallenge(Challenge challenge) throws RemoteException {
        return rejectChallenge(challenge);
    }

    /**
     * Updates the user
     * @param user user
     * @return if update is successful
     * @throws RemoteException Remote exception
     */
    @Override
    public boolean updateUser(User user) throws RemoteException {
        try{
            persistence.updateUser(user);
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns user
     * @param username username
     * @return user
     * @throws RemoteException Remote exception
     */
    @Override
    public User getUser(String username) throws RemoteException {
        try{
            User user = persistence.getUser(username);
            return user;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Validates the tournament
     * @param tournament tournament
     * @return tournament id
     * @throws RemoteException Remote exception
     */
    @Override
    public int validateTournament(Tournament tournament) throws RemoteException{
        try {
            int id = persistence.createTournament(tournament.getCreator(), tournament.getTurnTime(), tournament.getNrOfParticipants());
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Join a tournament
     * @param username username
     * @param tournamentID tournament id
     * @return if join is successful
     * @throws RemoteException Remote exception
     */
    @Override
    public boolean joinATournament(String username, int tournamentID) throws RemoteException {
        try {
            persistence.createTournamentParticipation(username, tournamentID);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns a tournament by id
     * @param id id
     * @return tournament
     * @throws RemoteException Remote exception
     */
    @Override
    public Tournament getTournamentById(int id) throws RemoteException{
        try {
            ArrayList<Tournament> tournaments = persistence.loadTournaments();
            for (Tournament i : tournaments) {
               if (i.getTournamentId() == id){
                   return i;
               }
           }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns all tournaments for a user
     * @param username username
     * @return tournaments for a user
     * @throws RemoteException Remote exception
     */
    @Override
    public ArrayList<Tournament> getAllTournamentsWhereAUserHasBeen(String username) throws RemoteException{
        try {
            return persistence.loadTournamentsForASpecificUser(username);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the top players of a tournament
     * @param tournamentID tournament id
     * @return top players of a tournament
     * @throws RemoteException Remote exception
     */
    @Override
    public ArrayList<TournamentParticipation> getTopPlayersInATournament(int tournamentID) throws RemoteException {
        try {
            return persistence.getTopPlayersInATournament(tournamentID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Sets the tournament outcome
     * @param tournamentId tournament id
     * @param finished status
     * @throws RemoteException Remote exception
     */
    @Override
    public void setTournamentOutcome(int tournamentId, boolean finished) throws RemoteException {
        try {
            persistence.setTournamentOutcome(tournamentId, finished);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Returns the tournament participants
     * @param id id
     * @return tournament participants
     * @throws RemoteException Remote exception
     */
    @Override
    public ArrayList<TournamentParticipation> getTournamentParticipationByTournamentID(int id) throws RemoteException{
        try {
          return persistence.loadTournamentParticipants(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates the nr of tournament participants
     * @param ID id
     * @param newSize new size
     * @throws RemoteException Remote exception
     */
    @Override
    public void updateTournamentNrOfParticipants(int ID, int newSize) throws RemoteException {
        try {
            persistence.updateTournamentNrOfParticipants(ID, newSize);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the participants placement
     * @param username username
     * @param placement placement
     * @param tournamentId id
     * @throws RemoteException Remote exception
     */
    @Override
    public void updateParticipantsPlacement(String username, int placement, int tournamentId) throws RemoteException {
        try {
            persistence.updateParticipantsPlacement(username, placement, tournamentId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Increments the win loss or draw attributes
     * @param username username
     * @param type type
     * @throws RemoteException Remote exception
     */
    @Override
    public void incrementWinLossDraw(String username, String type) throws RemoteException {
        try {
            persistence.incrementWinLossDraw(username, type);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
