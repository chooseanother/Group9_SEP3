using System;
using System.Text.Json;
using System.Threading.Tasks;
using Group9_SEP3_Chess.Models;

namespace Group9_SEP3_Chess.Data
{
    public class UserWebService : IUserService
    {
        private IRabbitMQ _rabbitMq;

        public UserWebService(IRabbitMQ rabbitMq)
        {
            _rabbitMq = rabbitMq;
        }

        public async Task<string> RegisterUserAsync(User user)
        {
            var response = await _rabbitMq.SendRequestAsync(new Message
            {
                Action = "Register",
                Email = user.Email,
                Username = user.Username,
                Password = user.Password
            });
            return response.Action;
        }

        public async Task<User> ValidateLogin(string userName, string password)
        {
            User user = new User
            {
                Username = userName,
                Password = password
            };
            var response = await _rabbitMq.SendRequestAsync(new Message
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
            else
            {
                throw new Exception($"{response.Action}");
            }
        }
    }
}