package RMI;

import model.*;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * @author group9
 * @version 1.0
 */

public class Tier2RMIClient extends UnicastRemoteObject implements ITier2RMIClient {
    private ITier3RMIServer tier3;

    /**
     * Creates the tier 2 rmi client
     * @throws RemoteException Remote Exception
     */
    public Tier2RMIClient() throws RemoteException {
        try {
            tier3 = (ITier3RMIServer) Naming.lookup(ITier3RMIServer.T3_SERVICE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Registers the user
     * @param username username
     * @param password password
     * @param email email
     * @return registration status
     * @throws RemoteException RemoteException
     */
    @Override
    public boolean registerUser(String username, String password, String email) throws RemoteException {
        try {
            return tier3.registerUser(new User(username, password, email));
        } catch (IllegalArgumentException e) {
            return false;
        }

    }

    /**
     * Moves the piece
     * @param piece piece
     * @param matchId match id
     * @return moving status
     * @throws RemoteException RemoteException
     */
    @Override
    public boolean movePiece(ChessPiece piece, int matchId) throws RemoteException {
        try {
            return tier3.movePiece(matchId, piece.getType(), piece.getColor(), piece.getOldPosition().getVerticalAxis() + ":" + piece.getOldPosition().getHorizontalAxis()
                    , piece.getNewPosition().getVerticalAxis() + ":" + piece.getNewPosition().getHorizontalAxis());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Validates the challenge
     * @param challenge challenge
     * @return validation status
     * @throws RemoteException RemoteException
     */
    @Override
    public boolean validateChallenge(Challenge challenge) throws RemoteException {
        try {
            return tier3.validateChallenge(challenge);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Loads the challenges
     * @return challenges
     * @throws RemoteException RemoteException
     */
    @Override
    public ArrayList<Challenge> loadChallenges() throws RemoteException {
        try {
            ArrayList<Challenge> challenges = tier3.loadChallenges();
            return challenges;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Loads challenges by username
     * @param username username
     * @return challenges
     * @throws RemoteException RemoteException
     */
    @Override
    public ArrayList<Challenge> loadChallenges(String username) throws RemoteException {
        try {
            ArrayList<Challenge> challenges = tier3.loadChallenges(username);
            return challenges;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Upgrades the piece
     * @param chessPiece piece
     * @param matchID match id
     * @return upgrade status
     */
    @Override
    public boolean upgradePiece(ChessPiece chessPiece, int matchID) {
        try {
            return tier3.upgradePiece(matchID, chessPiece.getType(), chessPiece.getColor(), chessPiece.getOldPosition().getVerticalAxis() + ":" + chessPiece.getOldPosition().getHorizontalAxis()
                    , chessPiece.getNewPosition().getVerticalAxis() + ":" + chessPiece.getNewPosition().getHorizontalAxis());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Rejects the challenge
     * @param challenge challenge
     * @return rejection status
     * @throws RemoteException RemoteException
     */
    @Override
    public boolean rejectChallenge(Challenge challenge) throws RemoteException {
        try {
            return tier3.rejectChallenge(challenge);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Validates the login
     * @param username username
     * @param password password
     * @return user
     * @throws RemoteException RemoteException
     */
    @Override
    public User validateLogin(String username, String password)
            throws RemoteException {
        try {
            return tier3.validateLogin(username, password);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Updates the match users turn
     * @param matchID match id
     * @param color color
     * @return update status
     * @throws RemoteException RemoteException
     */
    @Override
    public boolean updateMatchUsersTurn(int matchID, String color) throws RemoteException {
        try {
            return tier3.updateMatchUsersTurn(matchID, color);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates the user
     * @param user user
     * @return update status
     * @throws RemoteException RemoteException
     */
    @Override
    public boolean updateUser(User user) throws RemoteException {
        try {
            return tier3.updateUser(user);
        } catch (RemoteException e) {
            return false;
        }
    }

    /**
     * Returns the user
     * @param username username
     * @return user
     * @throws RemoteException RemoteException
     */
    @Override
    public User getUser(String username) throws RemoteException {
        try {
            return tier3.getUser(username);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Validates the tournament
     * @param tournament tournament
     * @return id
     * @throws RemoteException RemoteException
     */
    @Override
    public int validateTournament(Tournament tournament) throws RemoteException {
        try {
            int Id = tier3.validateTournament(tournament);
            return Id;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * join a tournament
     * @param username username
     * @param tournamentID tournament id
     * @return join status
     * @throws RemoteException RemoteException
     */
    @Override
    public boolean joinATournament(String username, int tournamentID) throws RemoteException {
        try {
            return tier3.joinATournament(username, tournamentID);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns the tournament by id
     * @param id id
     * @return tournament
     * @throws RemoteException RemoteException
     */
    @Override
    public Tournament getTournamentById(int id) throws RemoteException {
        try {
            return tier3.getTournamentById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the tournament participants by id
     * @param id id
     * @return tournament participants
     * @throws RemoteException RemoteException
     */
    @Override
    public ArrayList<TournamentParticipation> getTournamentParticipationByTournamentID(int id) throws RemoteException {
        try {
            return tier3.getTournamentParticipationByTournamentID(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the moves
     * @param matchID match id
     * @return moves
     * @throws RemoteException RemoteException
     */
    @Override
    public ArrayList<Move> getMoves(int matchID) throws RemoteException {
        return tier3.getMoves(matchID);
    }

    /**
     * Returns the matches
     * @param username username
     * @return matches
     * @throws RemoteException RemoteException
     */
    @Override public ArrayList<Match> getMatches(String username)
        throws RemoteException
    {
        return tier3.getMatches(username);
    }

    /**
     * Gets participants ina  match
     * @param matchId id
     * @return participants
     * @throws RemoteException RemoteException
     */
    @Override public ArrayList<Participant> getParticipants(int matchId)
        throws RemoteException
    {
        return tier3.getParticipants(matchId);
    }

    /**
     * Creates a match
     * @param turnTime turn time
     * @return match
     * @throws RemoteException RemoteException
     */
    @Override
    public Match createMatch(int turnTime) throws RemoteException {
        try {
            return tier3.createMatch(turnTime);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates the match
     * @param turnTime turn time
     * @param tournamentID tournament id
     * @return match
     * @throws RemoteException RemoteException
     */
    @Override
    public Match createMatch(int turnTime, int tournamentID) throws RemoteException {
        try {
            return tier3.createMatch(turnTime, tournamentID);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates the participants placement
     * @param username username
     * @param placement placement
     * @param tournamentId tournament id
     * @throws RemoteException RemoteException
     */
    @Override
    public void updateParticipantsPlacement(String username, int placement, int tournamentId) throws RemoteException {
        try {
            tier3.updateParticipantsPlacement(username, placement, tournamentId);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the participation
     * @param username username
     * @param color color
     * @param matchId match id
     * @return participation status
     * @throws RemoteException RemoteException
     */
    @Override
    public boolean createParticipation(String username, String color, int matchId) throws RemoteException {
        try {
            return tier3.createParticipation(username, color, matchId);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * removes the challenge
     * @param challenge challenge
     * @return remove status
     * @throws RemoteException RemoteException
     */
    @Override
    public boolean removeChallenge(Challenge challenge) throws RemoteException {
        try {
            return tier3.removeChallenge(challenge);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates the tournament nr or participants
     * @param ID id
     * @param newSize new size
     * @throws RemoteException RemoteException
     */
    @Override
    public void updateTournamentNrOfParticipants(int ID, int newSize) throws RemoteException {
        try {
            tier3.updateTournamentNrOfParticipants(ID, newSize);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the outcome
     * @param player player
     * @param outcome outcome
     * @param matchId match id
     * @throws RemoteException RemoteException
     */
    @Override
    public void updateOutcome(String player, String outcome, int matchId) throws RemoteException {
        try{
            tier3.updateOutcome(player, outcome, matchId);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    

    /**
     * Sets the match outcome
     * @param matchId match id
     * @param finished finished
     * @return set status
     * @throws RemoteException RemoteException
     */
    @Override
    public boolean setMatchOutcome(int matchId, boolean finished) throws RemoteException {
        return tier3.setMatchOutcome(matchId, finished);
    }

    /**
     * Returns the match by id
     * @param matchId id
     * @return match
     * @throws RemoteException RemoteException
     */
    @Override
    public Match getMatch(int matchId) throws RemoteException {
        return tier3.getMatch(matchId);
    }



    /**
     * Returns tournaments where a user has been
     * @param username username
     * @return tournaments
     * @throws RemoteException RemoteException
     */
    @Override
    public ArrayList<Tournament> getAllTournamentsWhereAUserHasBeen(String username) throws RemoteException {
        return tier3.getAllTournamentsWhereAUserHasBeen(username);
    }

    /**
     * Returns the top 3 players in a tournaments
     * @param tournamentID id
     * @return tournament participation
     * @throws RemoteException RemoteException
     */
    @Override
    public ArrayList<TournamentParticipation> getTopPlayersInATournament(int tournamentID) throws RemoteException {
        return tier3.getTopPlayersInATournament(tournamentID);
    }

    /**
     * Sets the tournament outcome
     * @param tournamentId id
     * @param finished status
     * @throws RemoteException RemoteException
     */
    @Override
    public void setTournamentOutcome(int tournamentId, boolean finished) throws RemoteException {
        tier3.setTournamentOutcome(tournamentId, finished);
    }

    /**
     * Method to increment win loss or draw
     * @param username username
     * @param type type
     * @throws RemoteException RemoteException
     */
    @Override
    public void incrementWinLossDraw(String username, String type) throws RemoteException {
        tier3.incrementWinLossDraw(username, type);
    }
}
