using System;
using System.Collections.Generic;
using System.Text.Json;
using System.Threading.Tasks;
using Group9_SEP3_Chess.Models;

namespace Group9_SEP3_Chess.Data
{
    public class ChallengeRmqService : IChallengeService
    {
        private readonly IRabbitMqService rabbitMqService;

        public ChallengeRmqService(IRabbitMqService rabbitMqService)
        {
            this.rabbitMqService = rabbitMqService;
        }

        public async Task<string> ChallengeUserAsync(Challenge challenge)
        {
            var jsonChallenge = JsonSerializer.Serialize(challenge);
            var message = new Message {Action = "Create challenge",Data=jsonChallenge};
            Console.WriteLine(message);

            var response = await rabbitMqService.SendRequestAsync(message);
            
            return response.Action;
        }

        public async Task<IList<Challenge>> GetChallengesAsync(string username)
        {
            var response = await rabbitMqService.SendRequestAsync(new Message {Action = "Get challenges", Data = username});

            Console.WriteLine($"{response.Action} {response.Data}");
            return JsonSerializer.Deserialize<List<Challenge>>(response.Data, new JsonSerializerOptions
            {
                PropertyNamingPolicy = JsonNamingPolicy.CamelCase
            });
        }

        public async Task<bool> AcceptChallengeAsync(Challenge challenge)
        {
            var jsonChallenge = JsonSerializer.Serialize(challenge);
            var response =
                await rabbitMqService.SendRequestAsync(new Message {Action = "Accept challenge", Data = jsonChallenge});
            return response.Action.Equals("Success");
        }

        public async Task<bool> DeclineChallengeAsync(Challenge challenge)
        {
            var jsonChallenge = JsonSerializer.Serialize(challenge);
            var response =
                await rabbitMqService.SendRequestAsync(new Message {Action = "Reject challenge", Data = jsonChallenge});
            return response.Action.Equals("Success");
        }
    }
}