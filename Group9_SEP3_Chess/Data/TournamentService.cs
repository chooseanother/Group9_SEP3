using System;
using System.Collections.Generic;
using System.Text.Json;
using System.Threading.Tasks;
using Group9_SEP3_Chess.Models;

namespace Group9_SEP3_Chess.Data
{
    public class TournamentService : ITournament

    {
        private readonly IRabbitMQ rabbitMq;

        public TournamentService(IRabbitMQ rabbitMq)
        {
            this.rabbitMq = rabbitMq;
        }

        public async Task<string> CreateTournament(Tournament tournament)
        {
            var jsonTournament = JsonSerializer.Serialize(tournament);
            var message = new Message {Action = "CreateTournament", Data = jsonTournament};
            Console.WriteLine(message);

            var response = await rabbitMq.SendRequestAsync(message);
            return response.Data;
        }

        public async Task<bool> JoinATournament(string username, int tournamentId, int placement)
        {
            var response = await rabbitMq.SendRequestAsync(new Message
            {
                Action = "JoinTournament",
                Data = username,
                DataSlot2 = tournamentId + "",
                DataSlot3 = placement + ""
            });
            return response.Action.Equals("Success");
        }

        public async Task<IList<Tournament>> GetTournamentsByUser(string loggedInUser)
        {
            var response = await rabbitMq.SendRequestAsync(new Message
            {
                Action = "TournamentHistory",
                Data = JsonSerializer.Serialize(loggedInUser)
            });
            Console.WriteLine(response);
            
            if (response.Action.Equals("TournamentHistory"))
            {
                IList<Tournament> rm = JsonSerializer.Deserialize<IList<Tournament>>(response.Data);
                return rm;
            }
            else
            {
                throw new Exception($"{response.Action}");
            }
        }
    }
}