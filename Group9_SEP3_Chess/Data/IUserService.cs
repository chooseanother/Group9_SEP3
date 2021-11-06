using System.Threading;
using System.Threading.Tasks;
using Group9_SEP3_Chess.Models;

namespace Group9_SEP3_Chess.Data
{
    public interface IUserService
    {
        Task<Message> RegisterUserAsync(Message message, CancellationToken cancellationToken = default(CancellationToken));
        Task<string> CallAsync(string message, CancellationToken cancellationToken = default(CancellationToken));
        void Close();
    }
}