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
            var userJson = JsonSerializer.Serialize(user);
            var response = await _rabbitMq.SendRequestAsync(new Message
            {
                Action = "Register",
                Data = userJson
            });
            return response.Action;
        }
    }
}