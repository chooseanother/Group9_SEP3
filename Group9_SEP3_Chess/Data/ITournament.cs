using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Group9_SEP3_Chess.Models;

namespace Group9_SEP3_Chess.Data
{
    public interface ITournament
    {
        Task<string> CreateTournamentAsync(Tournament tournament);
        Task<bool> JoinATournamentAsync(string username, int tournamentId);
        Task<IList<Tournament>> GetTournamentsByUserAsync(string loggedInUser);
    }
}