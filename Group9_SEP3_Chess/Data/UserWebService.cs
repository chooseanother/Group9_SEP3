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
    }
}