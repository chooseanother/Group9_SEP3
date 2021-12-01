using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Group9_SEP3_Chess.Models;

namespace Group9_SEP3_Chess.Data
{
    public interface ITournament
    {
        Task<string> CreateTournament(Tournament tournament);
        Task<bool> JoinATournament(string username, int tournamentId, int placement);
        Task<IList<Tournament>> GetTournamentsByUser(string loggedInUser);
    }
}