package model;

import RMI.ITier2RMIClient;
import RMI.Tier2RMIClient;
import RabbitMQ.RabbitMQClient;
import RabbitMQ.RabbitMQClientController;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author group9
 * @version 1.0
 */

public class ModelManager implements Model {
    private ITier2RMIClient iTier2RMIClient;
    private RabbitMQClient rabbitMQClient;

    /**
     * Creating a model manager
     * @throws RemoteException shows an exception which occurs in data server
     */
    public ModelManager() throws RemoteException {
        iTier2RMIClient = new Tier2RMIClient();
        rabbitMQClient = new RabbitMQClientController(this);
        try {
            rabbitMQClient.initRPCQueue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Registers user
     * @param username username
     * @param password password
     * @param email email
     * @return string
     */
    @Override
    public String registerUser(String username, String password, String email) {
        try {
            if (iTier2RMIClient.registerUser(username, password, email))
                return "Registered successfully";
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return "Failed registration";
    }

    /**
     * Method to send mail
     * @param matchId match id
     * @param username username
     */
    @Override
    public void sendMail(int matchId, String username) {
        new Thread(()->{
            try {
                ArrayList<Participant> participants = iTier2RMIClient.getParticipants(matchId);
                for (Participant p : participants) {
                    if (!p.getUsername().equals(username)) {
                        User user = iTier2RMIClient.getUser(p.getUsername());
                        Email email = new Email();
                        email.sendEmail("NotSoBusyChess@gmail.com", user.getEmail(), user.getUsername(), matchId);
                    }
                }
            }
            catch (RemoteException e){
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Moves the chess piece
     * @param selected selected
     * @param matchID match id
     * @param username username
     * @return chess piece
     */
    @Override
    public ChessPiece moveChessPiece(ChessPiece selected, int matchID, String username) {
        try {
            if (getMatch(matchID).getFinished()){
                return null;
            }
            ChessPiece toMove = getChessBoard(matchID).handleMoveAttack(selected, iTier2RMIClient, matchID,username);
            if (toMove!=null) {
                sendMail(matchID,username);
            }
            return toMove;

        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Upgrades the chess piece
     * @param upgradeSelected upgrade selected
     * @param toUpgrade chess piece
     * @param matchID match id
     * @param username username
     * @return chess pice
     */
    @Override
    public ChessPiece upgradeChessPiece(String upgradeSelected, ChessPiece toUpgrade, int matchID, String username) {
        try {
            if (getMatch(matchID).getFinished()){
                return null;
            }
            ChessPiece upgraded = getChessBoard(matchID).handleUpgradeChessPiece(upgradeSelected, toUpgrade, iTier2RMIClient, matchID,username);
            if (upgraded == null) {
                System.out.println("Chess piece was not upgraded as it was not saved");
            }
            return upgraded;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns the chess board
     * @param matchID match id
     * @return chess board
     */
    @Override
    public ChessBoard getChessBoard(int matchID) {
        ChessBoard chessBoard = new ChessBoard();
        try {
            ArrayList<Move> moves = iTier2RMIClient.getMoves(matchID);
            if (moves.size() > 0) {
                for (Move m : moves) {
                    String[] start = m.getStartPosition().split(":");
                    String[] end = m.getEndPosition().split(":");
                    Position oldPosition = new Position(Integer.parseInt(start[0]), Integer.parseInt(start[1]));
                    Position newPosition = new Position(Integer.parseInt(end[0]), Integer.parseInt(end[1]));
                    ChessPiece toMove = new ChessPiece(m.getPiece(), m.getColor(), oldPosition, newPosition);
                    if (m.getStartPosition().equals(m.getEndPosition())) {
                        chessBoard.handleMoveAttack(toMove, null, matchID,null);
                        chessBoard.handleUpgradeChessPiece(m.getPiece(),toMove, null, matchID,null);

                    } else {
                        chessBoard.handleMoveAttack(toMove, null, matchID,null);
                    }
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return chessBoard;
    }

    /**
     * Validates the challenge
     * @param challenge challenge
     * @return status
     */
    @Override
    public String validateChallenge(Challenge challenge) {
        try {
            if (challenge.getChallenger().equals(challenge.getChallenged())) {
                return "Can't challenge your self";
            }
            if (iTier2RMIClient.validateChallenge(challenge))
                return "Success";
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return "User doesn't exist or you already challenged that user.";
    }

    /**
     * Validates the login
     * @param username username
     * @param password password
     * @return user
     */
    @Override
    public User validateLogin(String username, String password) {
        try {
            User userFromDB = iTier2RMIClient.validateLogin(username, password);
            if (userFromDB == null) {
                throw new IllegalArgumentException("Wrong username or password");
            }
            return userFromDB;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates the user
     * @param user user
     * @return user
     */
    @Override
    public User updateUser(User user) {
        try {
            if (iTier2RMIClient.updateUser(user)) {
                return iTier2RMIClient.getUser(user.getUsername());
            }
            throw new IllegalArgumentException("User details were not updated successfully. Please try different password and/or email");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * loads the challenges
     * @return challenges
     */
    @Override
    public ArrayList<Challenge> loadChallenges() {
        try {
            return iTier2RMIClient.loadChallenges();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Loads teh challenges for a user
     * @param username username
     * @return challenges
     */
    @Override
    public ArrayList<Challenge> loadChallenges(String username) {
        try {
            return iTier2RMIClient.loadChallenges(username);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Accepts the challenge
     * @param challenge challenge
     * @return status
     */
    @Override
    public boolean acceptChallenge(Challenge challenge) {
        try {
            Match match = iTier2RMIClient.createMatch(challenge.getTurnTime());
            iTier2RMIClient.createParticipation(challenge.getChallenger(), "White", match.getMatchID());
            iTier2RMIClient.createParticipation(challenge.getChallenged(), "Black", match.getMatchID());
            iTier2RMIClient.removeChallenge(challenge);
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Rejects the challenge
     * @param challenge challenge
     * @return reject status
     */
    @Override
    public boolean rejectChallenge(Challenge challenge) {
        try {
            return iTier2RMIClient.rejectChallenge(challenge);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Returns all the removed chess pieces
     * @param matchID match id
     * @return chess piece
     */
    @Override
    public ArrayList<ChessPiece> getRemovedChessPieces(int matchID) {
        return getChessBoard(matchID).getRemovedChessPieces();
    }

    /**
     * Returns the match scores
     * @param black for black or white
     * @param matchID match id
     * @return scores
     */
    @Override
    public int getMatchScores(boolean black, int matchID) {
        if (black){
            return getChessBoard(matchID).getScore("Black");
        } else {
            return getChessBoard(matchID).getScore("White");
        }
    }

    /**
     * Creates the tournament
     * @param tournament tournament
     * @return tournament id
     */
    @Override
    public int createTournament(Tournament tournament) {
        try {
            return iTier2RMIClient.validateTournament(tournament);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Joins a tournament
     * @param username username
     * @param tournamentID id
     * @return join status
     */
    @Override
    public boolean joinATournament(String username, int tournamentID) {
        try {
            Tournament toCheck = iTier2RMIClient.getTournamentById(tournamentID);
            int size = iTier2RMIClient.getTournamentParticipationByTournamentID(tournamentID).size();

            if(size < toCheck.getNrOfParticipants() && !toCheck.getStatus()){
                if (iTier2RMIClient.joinATournament(username, tournamentID)) {

                    StartTournamentMatches(tournamentID);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Starts the tournament matches
     * @param tournamentID id
     */
    public void StartTournamentMatches(int tournamentID) {
        try {
            Tournament tournament = iTier2RMIClient.getTournamentById(tournamentID);
            ArrayList<TournamentParticipation> tournamentParticipations = iTier2RMIClient.getTournamentParticipationByTournamentID(tournamentID);
            if (tournamentParticipations.size() == tournament.getNrOfParticipants()) {
                for (int i = 0; i < tournamentParticipations.size(); i += 2) {
                    Match match = iTier2RMIClient.createMatch(tournament.getTurnTime(), tournamentID);
                    iTier2RMIClient.createParticipation(tournamentParticipations.get(i).getUsername(), "White", match.getMatchID());
                    iTier2RMIClient.createParticipation(tournamentParticipations.get(i + 1).getUsername(), "Black", match.getMatchID());
                }

                if(tournamentParticipations.size() != 2){
                    iTier2RMIClient.updateTournamentNrOfParticipants(tournamentID, tournamentParticipations.size() / 2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks the turn time
     * @param match match
     * @return match
     */
    @Override
    public Match checkTurnTime(Match match) {
        if (match.getFinished()){
            return match;
        }
        Date moveDate = new Date(match.getLatestMove());
        Date currentDate = new Date();
        int difference = (int)(currentDate.getTime()-moveDate.getTime())/1000;
        int turnTime = match.getTurnTime();
        int matchId = match.getMatchID();
        if (difference > turnTime){
            Participant loser;
            Participant winner;
            if (match.getUsersTurn().equals("White")){
                loser = match.getWhitePlayer();
                winner = match.getBlackPlayer();
            }
            else {
                loser = match.getBlackPlayer();
                winner = match.getWhitePlayer();
            }
            try{
                iTier2RMIClient.updateOutcome(loser.getUsername(),"Loss", matchId);
                iTier2RMIClient.updateOutcome(winner.getUsername(), "Win", matchId);
                iTier2RMIClient.setMatchOutcome(matchId,true);

                if(iTier2RMIClient.getMatch(match.getMatchID()).getTournamentID() != 0){
                    int TournamentID = iTier2RMIClient.getMatch(matchId).getTournamentID();
                    iTier2RMIClient.updateParticipantsPlacement(loser.getUsername(),
                            iTier2RMIClient.getTournamentParticipationByTournamentID(TournamentID).size(), TournamentID);
                    StartTournamentMatches(TournamentID);

                    if (iTier2RMIClient.getTournamentParticipationByTournamentID(TournamentID).size() == 1) {
                        iTier2RMIClient.updateParticipantsPlacement(winner.getUsername(), 1, TournamentID);
                    }
                }

                iTier2RMIClient.incrementWinLossDraw(winner.getUsername(), "wins");// award win to winner
                iTier2RMIClient.incrementWinLossDraw(loser.getUsername(), "losses");// give loss to loser
            }
            catch (RemoteException e){
                e.printStackTrace();
            }
        }
        return match;
    }

    /**
     * Adds participants to a match
     * @param match match
     * @return participants
     */
    @Override
    public Match addParticipantsToMatch(Match match) {
        try {
            ArrayList<Participant> participants = iTier2RMIClient.getParticipants(match.getMatchID());
            for(Participant p: participants){
                if(p.getColor().equals("Black")){
                    match.setBlackPlayer(p);
                }
                else{
                    match.setWhitePlayer(p);
                }
            }
            return match;
        } catch (RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns the match
     * @param matchId id
     * @return match
     */
    @Override
    public Match getMatch(int matchId) {
        try{
            Match match = iTier2RMIClient.getMatch(matchId);
            match = addParticipantsToMatch(match);
            match = checkTurnTime(match);
            return match;
        } catch (RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns the matches
     * @param username username
     * @return matches
     */
    @Override public ArrayList<Match> getMatches(String username)
    {
        try{
            ArrayList<Match> matches = iTier2RMIClient.getMatches(username);
            for (Match m : matches){
                addParticipantsToMatch(m);
                checkTurnTime(m);
            }
            matches.removeIf(Match::getFinished);
            return matches;
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the match history
     * @param username username
     * @return match history
     */
    @Override public ArrayList<Match> getMatchHistory(String username)
    {
        try{
            ArrayList<Match> matches = iTier2RMIClient.getMatches(username);
            for (Match m : matches){
                addParticipantsToMatch(m);
                checkTurnTime(m);
            }
            matches.removeIf(m -> !m.getFinished());
            return matches;
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates the match outcome
     * @param player player
     * @param outcome outcome
     * @param matchId match id
     */
    @Override
    public void updateOutcome(String player, String outcome, int matchId) {
        try {
            if (getMatch(matchId).getFinished()){
                return;
            }
            if (iTier2RMIClient.getMatch(matchId).getTournamentID() == 0) {
                iTier2RMIClient.updateOutcome(player, outcome, matchId);

                ArrayList<Participant> participants = iTier2RMIClient.getParticipants(matchId);

                Participant player1 = participants.get(0);
                Participant player2 = participants.get(1);

                if(player1.getOutcome() != null && player2.getOutcome() != null) {
                    switch (player1.getOutcome()) {
                        case "Draw":
                            if (player2.getOutcome().equals("Draw")) {
                                iTier2RMIClient.setMatchOutcome(matchId, true);
                                iTier2RMIClient.incrementWinLossDraw(player1.getUsername(), "draws");
                                iTier2RMIClient.incrementWinLossDraw(player2.getUsername(), "draws");
                            }
                            break;
                        case "Win":
                            if (player2.getOutcome().equals("Loss")) {
                                iTier2RMIClient.setMatchOutcome(matchId, true);
                                iTier2RMIClient.incrementWinLossDraw(player1.getUsername(), "wins");
                                iTier2RMIClient.incrementWinLossDraw(player2.getUsername(), "losses");
                            }
                            break;
                        case "Loss":
                            if (player2.getOutcome().equals("Win")) {
                                iTier2RMIClient.setMatchOutcome(matchId, true);
                                iTier2RMIClient.incrementWinLossDraw(player2.getUsername(), "wins");
                                iTier2RMIClient.incrementWinLossDraw(player1.getUsername(), "losses");
                            }
                            break;
                        default:
                    }
                }
            } else {
                iTier2RMIClient.updateOutcome(player, outcome, matchId);
                ArrayList<Participant> participants = iTier2RMIClient.getParticipants(matchId);

                int TournamentID = iTier2RMIClient.getMatch(matchId).getTournamentID();
                ArrayList<TournamentParticipation> currentParticipants = iTier2RMIClient.getTournamentParticipationByTournamentID(TournamentID);
                int Placement = currentParticipants.size();


                Participant player1 = participants.get(0);
                Participant player2 = participants.get(1);

                if (player1.getOutcome() != null && player2.getOutcome() != null) {
                    switch (player1.getOutcome()) {
                        case "Draw":
                            if (player2.getOutcome().equals("Draw")) {
                                iTier2RMIClient.setMatchOutcome(matchId, true);
                                iTier2RMIClient.incrementWinLossDraw(player1.getUsername(), "draws");
                                iTier2RMIClient.incrementWinLossDraw(player2.getUsername(), "draws");
                                if (player1.getColor().equals("White")) {
                                    if (getMatchScores(false, matchId) > getMatchScores(true, matchId)) {
                                        iTier2RMIClient.updateParticipantsPlacement(player2.getUsername(), Placement, TournamentID);
                                    } else {
                                        iTier2RMIClient.updateParticipantsPlacement(player1.getUsername(), Placement, TournamentID);
                                    }
                                }
                            }
                            StartTournamentMatches(TournamentID);
                            break;
                        case "Win":
                            if (player2.getOutcome().equals("Loss")) {
                                iTier2RMIClient.setMatchOutcome(matchId, true);
                                iTier2RMIClient.incrementWinLossDraw(player1.getUsername(), "wins");
                                iTier2RMIClient.incrementWinLossDraw(player2.getUsername(), "losses");
                                iTier2RMIClient.updateParticipantsPlacement(player2.getUsername(), Placement, TournamentID);
                            }
                            StartTournamentMatches(TournamentID);
                            break;
                        case "Loss":
                            if (player2.getOutcome().equals("Win")) {
                                iTier2RMIClient.setMatchOutcome(matchId, true);
                                iTier2RMIClient.incrementWinLossDraw(player2.getUsername(), "wins");
                                iTier2RMIClient.incrementWinLossDraw(player1.getUsername(), "losses");
                                iTier2RMIClient.updateParticipantsPlacement(player1.getUsername(), Placement, TournamentID);
                            }
                            StartTournamentMatches(TournamentID);
                            break;
                        default:
                    }
                    if( iTier2RMIClient.getTournamentParticipationByTournamentID(TournamentID).size() == 1){
                        iTier2RMIClient.updateParticipantsPlacement(currentParticipants.get(0).getUsername(), 1, TournamentID);
                        iTier2RMIClient.setTournamentOutcome(TournamentID, true);
                    }
                }
            }
        }catch (RemoteException e){
            e.printStackTrace();
        }
    }

    /**
     * Returns the moves
     * @param matchId id
     * @return moves
     */
    public ArrayList<Move> getMoves(int matchId){
        try
        {
            return iTier2RMIClient.getMoves(matchId);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns all the tournaments where a user has been
     * @param username username
     * @return tournaments
     */
    @Override
    public ArrayList<Tournament> getAllTournamentsWhereAUserHasBeen (String username){
        try {
            ArrayList<Tournament> toReturn = iTier2RMIClient.getAllTournamentsWhereAUserHasBeen(username);
            loadTop3PlayersInTournament(toReturn);
            return toReturn;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Loads the top 3 players from a tournament
     * @param tournamentsToLoad top 3 players from a tournament
     */
    @Override
    public void loadTop3PlayersInTournament(ArrayList<Tournament> tournamentsToLoad){
        try {
            for(Tournament t: tournamentsToLoad){
                ArrayList<TournamentParticipation> tournamentParticipation = iTier2RMIClient.getTopPlayersInATournament(t.getTournamentId());

                if(iTier2RMIClient.getTournamentParticipationByTournamentID(t.getTournamentId()).size() == 0){
                    t.setTop3Players("1: " + tournamentParticipation.get(0).getUsername() + ", 2: " + tournamentParticipation.get(1).getUsername() +
                            ", 3: " + tournamentParticipation.get(2).getUsername());
                } else {
                    t.setTop3Players("Tournament not Finished");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
