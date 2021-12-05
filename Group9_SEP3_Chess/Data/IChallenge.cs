using System.Collections.Generic;
using System.Threading.Tasks;
using Group9_SEP3_Chess.Models;

namespace Group9_SEP3_Chess.Data
{
    public interface IChallenge
    {
        Task<string> ChallengeUserAsync(Challenge challenge);
        Task<IList<Challenge>> GetChallengesAsync(string username);
        Task<bool> AcceptChallengeAsync(Challenge challenge);
        Task<bool> DeclineChallengeAsync(Challenge challenge);
    }
}