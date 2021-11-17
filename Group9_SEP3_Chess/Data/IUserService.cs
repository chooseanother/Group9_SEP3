using System.Threading;
using System.Threading.Tasks;
using Group9_SEP3_Chess.Models;

namespace Group9_SEP3_Chess.Data
{
    public interface IUserService
    {
        Task<string> RegisterUserAsync(User user);
        Task<User> ValidateLogin(string userName, string password);
    }
}