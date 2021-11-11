using System.Threading.Tasks;
using Group9_SEP3_Chess.Models;

namespace Group9_SEP3_Chess.Data
{
    public interface IPlayMatch
    {
        Task ChallengeUser(Challenge challenge);
    }
}