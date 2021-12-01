using System;
using System.Collections.Generic;
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
            m1.DataSlot2 = tournamentID + "";
            m1.DataSlot3 = placement + "";
            var response = await _rabbitMq.SendRequestAsync(m1);
            return response.Action.Equals("Success");
        }

        public async Task<IList<Tournament>> GetTournamentsByUser(string loggedInUser)
        {
            Message response = await _rabbitMq.SendRequestAsync(new Message
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