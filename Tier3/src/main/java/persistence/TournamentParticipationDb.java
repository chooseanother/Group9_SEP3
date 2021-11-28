package persistence;

import model.Challenge;
import model.Tournament;
import model.TournamentParticipation;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TournamentParticipationDb implements TournamentParticipationPersistence{
    @Override
    public int CreateTournamentParticipation(String username, int tournamentID, int placement) throws SQLException {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement2 = connection.prepareStatement("INSERT INTO TOURNAMENT_PARTICIPATION (USERNAME, TOURNAMENTID, PLACEMENT) VALUES (?,?,?)");
            statement2.setString(1, username);
            statement2.setInt(2, tournamentID);
            statement2.setInt(3, placement);
            statement2.executeUpdate();
        }
        return 1;
    }

    @Override
    public ArrayList<TournamentParticipation> loadTournamentParticipants(int tournamentID) throws SQLException {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select * from TOURNAMENT_PARTICIPATION where TOURNAMENTID = ? AND PLACEMENT = 0");
            statement.setInt(1, tournamentID);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<TournamentParticipation> tournamentParticipations = new ArrayList<>();

            while (resultSet.next()) {
                String username = resultSet.getString("USERNAME");
                int TournamentId = resultSet.getInt("TOURNAMENTID");
                int placement = resultSet.getInt("PLACEMENT");
                tournamentParticipations.add(new TournamentParticipation(username, TournamentId, placement));
            }
            return tournamentParticipations;
        }
    }

    @Override
    public void UpdateParticipantsPlacement(String username, int placement, int tournamentId) throws SQLException {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE TOURNAMENT_PARTICIPATION SET PLACEMENT = ? WHERE USERNAME = ? AND TOURNAMENTID = ?");
            statement.setInt(1, placement);
            statement.setString(2, username);
            statement.setInt(3, tournamentId);
            statement.executeUpdate();
        }
    }

}
