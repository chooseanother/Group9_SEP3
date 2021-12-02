using System;
using System.Text.Json;
using System.Threading.Tasks;
using Group9_SEP3_Chess.Models;


namespace Group9_SEP3_Chess.Data
{
    public class UserService : IUserService
    {
        private readonly IRabbitMQ rabbitMq;

        public UserService(IRabbitMQ rabbitMq)
        {
            this.rabbitMq = rabbitMq;
        }

        public async Task<string> RegisterUserAsync(User user)
        {
            var userJson = JsonSerializer.Serialize(user);
            var response = await rabbitMq.SendRequestAsync(new Message
            {
                Action = "Register",
                Data = userJson
            });
            return response.Action;
        }

        public async Task<User> ValidateLogin(string username, string password)
        {
            var user = new User
            {
                Username = username,
                Password = password
            };
            var response = await rabbitMq.SendRequestAsync(new Message
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

        public async Task<User> UpdateUser(User user)
        {
            var response = await rabbitMq.SendRequestAsync(new Message
            {
                Action = "UpdateUser",
                Data = JsonSerializer.Serialize(user)
                });
            if (response.Action.Equals("UserUpdated"))
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