using System;
using System.Collections.Generic;
using System.Text.RegularExpressions;
using System.Threading;
using System.Threading.Tasks;
using Group9_SEP3_Chess.Models;
using Match = Group9_SEP3_Chess.Models.Match;

namespace Group9_SEP3_Chess.Data
{
    public interface IMatchService
    {
        Task<ChessPiece> MoveChessPiece(Message message, CancellationToken cancellationToken = default(CancellationToken));
        Task<ChessPiece> UpgradeChessPiece(Message message, CancellationToken cancellationToken = default(CancellationToken));

        Task<ChessPiece[,]> LoadChessPieces(Message message,
            CancellationToken cancellationToken = default(CancellationToken));
        public IList<ChessPiece> getBlackRemovedChessPieces();
        public IList<ChessPiece> getWhiteRemovedChessPieces();
        String getMatchScores(bool Black);

        Task<IList<Match>> GetMatches(string loggedInUser);
        Task<Match> GetMatch(int matchId);
        Task UpdateOutcome(string username, string outcome, int matchId);
        
        Task<IList<Match>> GetFinishedMatches(string loggedInUser);

        Task<IList<Move>> GetMoves(int matchId);

    }
}