package persistence;

import model.Challenge;
import model.Tournament;
import model.TournamentParticipation;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            PreparedStatement statement = connection.prepareStatement("select USERNAME from TOURNAMENT_PARTICIPATION where TOURNAMENTID = ?");
            statement.setInt(1, tournamentID);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<TournamentParticipation> tournamentParticipations = new ArrayList<>();

            while (resultSet.next()) {
                String username = resultSet.getString("USERNAME");
                int TournamentId = resultSet.getInt("TOURNAMENTID");
                int placement = resultSet.getInt("PLACEMENT");
                tournamentParticipations.add(new TournamentParticipation(username, tournamentID, placement));
            }
            return tournamentParticipations;
        }
    }

}
