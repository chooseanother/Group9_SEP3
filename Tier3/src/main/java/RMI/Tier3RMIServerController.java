package RMI;/*
 * 12.09.2018 Original version
 */

import model.Challenge;
import model.Tournament;
import model.TournamentParticipation;
import model.Match;
import model.Move;
import model.User;
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


public class Tier3RMIServerController
        extends UnicastRemoteObject
        implements ITier3RMIServer {
    private Persistence persistence;

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

    private void startRegistry() throws RemoteException {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            System.out.println("Registry started");
        } catch (ExportException e) {
            System.err.println("Server exception: " + e);
            e.printStackTrace();
        }
    }

    private void startServer() throws MalformedURLException, RemoteException {
        Naming.rebind(T3_SERVICE_NAME, this);
        System.out.println("Server ready");
    }

    @Override
    public boolean registerUser(User user) throws RemoteException {
        try {
            persistence.registerUser(user);
            System.out.println(user.getUsername() + " was created.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean MovePiece(int matchId, String piece, String color, String startPosition, String endPosition) {
        try {
            persistence.MovePiece(matchId, piece, color, startPosition, endPosition);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean validateChallenge(Challenge challenge) throws RemoteException {
        try {
            persistence.createChallenge(challenge);
            System.out.println(challenge + " was created.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean UpgradePiece(int matchId, String piece, String color, String startPosition, String endPosition) throws RemoteException {
        try {
            persistence.UpgradePiece(matchId, piece, color, startPosition, endPosition);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<Move> getMoves(int matchID) throws RemoteException {
        try {
            return persistence.getMoves(matchID);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Challenge> loadChallenges() throws RemoteException {
        try {
            ArrayList<Challenge> challenges = persistence.loadChallenges();
            return challenges;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Challenge> loadChallenges(String username) throws RemoteException {
        try {
            ArrayList<Challenge> challenges = persistence.loadChallenges(username);
            return challenges;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

//    @Override
//    public boolean acceptChallenge(Challenge challenge) throws RemoteException {
//        try {
//            int matchId = persistence.createMatch(challenge.getTurnTime(), "Friendly");
//            persistence.createMatchParticipation(challenge.getChallenger(),"White",matchId);
//            persistence.createMatchParticipation(challenge.getChallenged(),"Black",matchId);
//            return persistence.deleteChallenge(challenge);
//        } catch (SQLException e){
//            e.printStackTrace();
//            return false;
//        }
//    }


    @Override
    public boolean rejectChallenge(Challenge challenge) throws RemoteException {
        try {
            return persistence.deleteChallenge(challenge);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User validateLogin(String username, String password)
            throws RemoteException {
        try {
            User user = persistence.validateLogin(username, password);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override

    public boolean UpdateMatchUserTurn(int matchID, String color) throws RemoteException {
        try {
            persistence.UpdateMatchUserTurn(matchID, color);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Match createMatch(int turnTime) throws RemoteException {
        try {
            return persistence.createMatch(turnTime, "Friendly");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean createParticipation(String username, String color, int matchId) throws RemoteException {
        try {
            persistence.createMatchParticipation(username, color, matchId);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeChallenge(Challenge challenge) throws RemoteException {
        return rejectChallenge(challenge);
    }

    @Override
    public boolean updateUser(User user) throws RemoteException {
        try {
            persistence.updateUser(user);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User getUser(String username) throws RemoteException {
        try {
            User user = persistence.getUser(username);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int validateTournament(Tournament tournament) throws RemoteException {
        try {
            int id = persistence.createTournament(tournament.getCreator(), tournament.getTurnTime(), tournament.getNrOfParticipants());
            System.out.println("Tournament was created by " + tournament.getCreator());
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public boolean joinATournament(String username, int tournamentID, int placement) throws RemoteException {
        try {
            persistence.CreateTournamentParticipation(username, tournamentID, placement);
            return true;
//           ArrayList<String> usernames = persistence.loadUsernamesOfPlayersInATournament(tournamentID);
//           ArrayList<Tournament> tournaments = persistence.loadTournaments();
//           Tournament tournament = null;
//
//           for (Tournament i :tournaments){
//               if (i.getTournamentId() == tournamentID){
//                   tournament = i;
//               }
//           }
//
//           switch (tournament.getNrOfParticipants()){
//               case 4:
//                   if(usernames.size() == 4){
//                       //First 2
//                       int matchId = persistence.createMatch(tournament.getTurnTime(), "Friendly");
//                       persistence.createMatchParticipation(usernames.get(0), "White", matchId);
//                       persistence.createMatchParticipation(usernames.get(1), "Black", matchId);
//                       //second 2
//                       int matchId2 = persistence.createMatch(tournament.getTurnTime(), "Friendly");
//                       persistence.createMatchParticipation(usernames.get(2), "White", matchId2);
//                       persistence.createMatchParticipation(usernames.get(3), "Black", matchId2);
//                       // wait for match or something...
//                   } else {
//                       persistence.CreateTournamentParticipation(username,tournamentID,placement);
//                   }
//                   break;
//               case 8:
//                   break;
//               case 16:
//                   break;
//               case 32:
//                   break;
//           }
//           return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Tournament GetTournamentById(int id) {
        try {
            ArrayList<Tournament> tournaments = persistence.loadTournaments();
            for (Tournament i : tournaments) {
                if (i.getTournamentId() == id) {
                    return i;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<TournamentParticipation> getTournamentParticipationByTournamentID(int id) {
        try {
            return persistence.loadTournamentParticipants(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
