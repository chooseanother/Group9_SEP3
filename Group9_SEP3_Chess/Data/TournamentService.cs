using System;
using System.Text.Json;
using System.Threading.Tasks;
using Group9_SEP3_Chess.Models;

namespace Group9_SEP3_Chess.Data
{
    public class TournamentService : ITournament

    {
    private IRabbitMQ _rabbitMq;

    public TournamentService(IRabbitMQ rabbitMq)
    {
        _rabbitMq = rabbitMq;
    }

    public async Task<String> CreateTournament(Tournament tournament)
    {
        var jsonTournament = JsonSerializer.Serialize(tournament);
        var message = new Message {Action = "CreateTournament", Data = jsonTournament};
        Console.WriteLine(message);

        var response = await _rabbitMq.SendRequestAsync(message);
        return response.Data;
    }

    public async Task<bool> joinATournament(string username, int tournamentID, int placement)
    {
        Message m1 = new Message();
        m1.Action = "JoinTournament";
        m1.Data = username;
        m1.DataSlot2 = tournamentID+"";
        m1.DataSlot3 = placement + "";
        var response = await _rabbitMq.SendRequestAsync(m1);
        return response.Action.Equals("Success");
    }
    }
}