using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Group9_SEP3_Chess.Models;

namespace Group9_SEP3_Chess.Data
{
    public interface ITournament
    {
        Task<String> CreateTournament(Tournament tournament);
        Task<bool> joinATournament(String username, int tournamentID, int placement);
        Task<IList<Tournament>> GetTournamentsByUser(string loggedInUser);
    }
}