using System.Collections.Generic;
using System.Threading.Tasks;
using Group9_SEP3_Chess.Models;

namespace Group9_SEP3_Chess.Data
{
    public interface IChallenge
    {
        Task<string> ChallengeUser(Challenge challenge);
        Task<IList<Challenge>> GetChallenges(string username);
        Task<bool> AcceptChallenge(Challenge challenge);
        Task<bool> DeclineChallenge(Challenge challenge);
    }
}