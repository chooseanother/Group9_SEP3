using System;
using System.Text.Json;
using System.Threading.Tasks;
using Group9_SEP3_Chess.Models;


namespace Group9_SEP3_Chess.Data
{
    public class UserWebService : IUserService
    {
        private IRabbitMQ _rabbitMq;
        private User returnedUser;

        public UserWebService(IRabbitMQ rabbitMq)
        {
            _rabbitMq = rabbitMq;
        }

        public async Task<string> RegisterUserAsync(User user)
        {
            var userJson = JsonSerializer.Serialize(user);
            var response = await _rabbitMq.SendRequestAsync(new Message
            {
                Action = "Register",
                Data = userJson
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
                return  returnedUser = JsonSerializer.Deserialize<User>(response.Data, new JsonSerializerOptions
                {
                    PropertyNamingPolicy = JsonNamingPolicy.CamelCase
                });
            }
            else
            {
                throw new Exception($"{response.Action}");
            }
        }

        public async Task<User> UpdateUser(User user)
        {
            var response = await _rabbitMq.SendRequestAsync(new Message
            {
                Action = "UpdateUser",
                Data = JsonSerializer.Serialize(user)
            });
            if (response.Action.Equals("UserUpdated"))
            {
                return returnedUser =  JsonSerializer.Deserialize<User>(response.Data, new JsonSerializerOptions
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