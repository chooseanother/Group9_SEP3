using System;
using System.Collections.Generic;
using System.Text.Json;
using System.Threading.Tasks;
using Group9_SEP3_Chess.Models;

namespace Group9_SEP3_Chess.Data
{
    public class TournamentRmqService : ITournamentService

    {
        private readonly IRabbitMqService rabbitMqService;

        public TournamentRmqService(IRabbitMqService rabbitMqService)
        {
            this.rabbitMqService = rabbitMqService;
        }

        public async Task<string> CreateTournamentAsync(Tournament tournament)
        {
            var jsonTournament = JsonSerializer.Serialize(tournament);
            var message = new Message {Action = "CreateTournament", Data = jsonTournament};
            Console.WriteLine(message);

            var response = await rabbitMqService.SendRequestAsync(message);
            return response.Data;
        }

        public async Task<bool> JoinATournamentAsync(string username, int tournamentId)
        {
            var response = await rabbitMqService.SendRequestAsync(new Message
            {
                Action = "JoinTournament",
                Data = username,
                DataSlot2 = tournamentId + "",
                DataSlot3 = "0"
            });
            return response.Action.Equals("Success");
        }

        public async Task<IList<Tournament>> GetTournamentsByUserAsync(string loggedInUser)
        {
            var response = await rabbitMqService.SendRequestAsync(new Message
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