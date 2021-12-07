using System;
using System.Text.Json;
using System.Threading.Tasks;
using Group9_SEP3_Chess.Models;


namespace Group9_SEP3_Chess.Data
{
    public class UserRmqService : IUserService
    {
        private readonly IRabbitMqService rabbitMqService;

        public UserRmqService(IRabbitMqService rabbitMqService)
        {
            this.rabbitMqService = rabbitMqService;
        }

        public async Task<string> RegisterUserAsync(User user)
        {
            var userJson = JsonSerializer.Serialize(user);
            var response = await rabbitMqService.SendRequestAsync(new Message
            {
                Action = "Register",
                Data = userJson
            });
            return response.Action;
        }

        public async Task<User> ValidateLoginAsync(string username, string password)
        {
            var user = new User
            {
                Username = username,
                Password = password
            };
            var response = await rabbitMqService.SendRequestAsync(new Message
            {
                Action = "Login",
                Data = JsonSerializer.Serialize(user)
            });
            if (response.Action.Equals("LoggedIn"))
            {
                return JsonSerializer.Deserialize<User>(response.Data, new JsonSerializerOptions
                {
                    PropertyNamingPolicy = JsonNamingPolicy.CamelCase
                });
            }
            throw new Exception($"{response.Action}");
        }

        public async Task<User> UpdateUserAsync(User user)
        {
            var response = await rabbitMqService.SendRequestAsync(new Message
            {
                Action = "UpdateUser",
                Data = JsonSerializer.Serialize(user)
                });
            if (response.Action.Equals("User updated"))
            {
                return JsonSerializer.Deserialize<User>(response.Data, new JsonSerializerOptions
                {
                    PropertyNamingPolicy = JsonNamingPolicy.CamelCase
                });
            }
            throw new Exception($"{response.Action}");
        }
    }
}