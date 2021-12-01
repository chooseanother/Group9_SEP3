package RMI;

import model.*;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;


public class Tier2RMIClient extends UnicastRemoteObject implements ITier2RMIClient {
    private ITier3RMIServer tier3;

    public Tier2RMIClient() throws RemoteException {
        try {

            tier3 = (ITier3RMIServer) Naming.lookup(ITier3RMIServer.T3_SERVICE_NAME);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean registerUser(String username, String password, String email) throws RemoteException {

        try {
            return tier3.registerUser(new User(username, password, email));
        } catch (IllegalArgumentException e) {
            return false;
            // actions to counter illegal data
        }

    }

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

    @Override
    public boolean validateChallenge(Challenge challenge) throws RemoteException {
        try {
            return tier3.validateChallenge(challenge);
        } catch (IllegalArgumentException e) {
            return false;
            // actions to counter illegal data
        }
    }

    @Override
    public ArrayList<Challenge> loadChallenges() throws RemoteException {
        try {
            ArrayList<Challenge> challenges = (ArrayList<Challenge>) tier3.loadChallenges();
            return challenges;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Challenge> loadChallenges(String username) throws RemoteException {
        try {
            ArrayList<Challenge> challenges = (ArrayList<Challenge>) tier3.loadChallenges(username);
            return challenges;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

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

    @Override
    public boolean rejectChallenge(Challenge challenge) throws RemoteException {
        try {
            return tier3.rejectChallenge(challenge);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User validateLogin(String username, String password)
            throws RemoteException {
        try {
            return tier3.validateLogin(username, password);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public boolean updateMatchUsersTurn(int matchID, String color) throws RemoteException {
        try {
            return tier3.updateMatchUsersTurn(matchID, color);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateUser(User user) throws RemoteException {
        try {
            return tier3.updateUser(user);
        } catch (RemoteException e) {
            return false;
        }
    }

    @Override
    public User getUser(String username) throws RemoteException {
        try {
            return tier3.getUser(username);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

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

    @Override
    public boolean joinATournament(String username, int tournamentID, int placement) throws RemoteException {
        try {
            return tier3.joinATournament(username, tournamentID, placement);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Tournament getTournamentById(int id) throws RemoteException {
        try {
            return tier3.getTournamentById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<TournamentParticipation> getTournamentParticipationByTournamentID(int id) throws RemoteException {
        try {
            return tier3.getTournamentParticipationByTournamentID(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Move> getMoves(int matchID) throws RemoteException {
        return tier3.getMoves(matchID);
    }

    @Override public ArrayList<Match> getMatches(String username)
        throws RemoteException
    {
        return tier3.getMatches(username);
    }

    @Override public ArrayList<Participant> getParticipants(int matchId)
        throws RemoteException
    {
        return tier3.getParticipants(matchId);
    }

    @Override
    public Match createMatch(int turnTime) throws RemoteException {
        try {
            return tier3.createMatch(turnTime);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Match createMatch(int turnTime, int tournamentID) throws RemoteException {
        try {
            return tier3.createMatch(turnTime, tournamentID);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateParticipantsPlacement(String username, int placement, int tournamentId) throws RemoteException {
        try {
            tier3.updateParticipantsPlacement(username, placement, tournamentId);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean createParticipation(String username, String color, int matchId) throws RemoteException {
        try {
            return tier3.createParticipation(username, color, matchId);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeChallenge(Challenge challenge) throws RemoteException {
        try {
            return tier3.removeChallenge(challenge);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void updateTournamentNrOfParticipants(int ID, int newSize) throws RemoteException {
        try {
            tier3.updateTournamentNrOfParticipants(ID, newSize);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateOutcome(String player, String outcome, int matchId) throws RemoteException {
        try{
            tier3.updateOutcome(player, outcome, matchId);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String getParticipationColor(String player, int matchId) throws RemoteException {
        return tier3.getParticipationColor(player, matchId);
    }

    @Override
    public boolean setMatchOutcome(int matchId, boolean finished) throws RemoteException {
        return tier3.setMatchOutcome(matchId, finished);
    }

    @Override
    public Match getMatch(int matchId) throws RemoteException {
        return tier3.getMatch(matchId);
    }

    @Override
    public int getNrofOriginalParticipants(int tournamentID) throws RemoteException {
        return tier3.getNrOfOriginalParticipants(tournamentID);
    }

    @Override
    public ArrayList<Tournament> getAllTournamentsWhereAUserHasBeen(String username) throws RemoteException {
        return tier3.getAllTournamentsWhereAUserHasBeen(username);
    }

    @Override
    public ArrayList<TournamentParticipation> getTopPlayersInATournament(int tournamentID) throws RemoteException {
        return tier3.getTopPlayersInATournament(tournamentID);
    }

    @Override
    public void incrementWinLossDraw(String username, String type) throws RemoteException {
        tier3.incrementWinLossDraw(username, type);
    }
}
