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
        Task<ChessPiece> MoveChessPieceAsync(Message message);
        Task<ChessPiece> UpgradeChessPieceAsync(Message message);
        Task<ChessPiece[,]> LoadChessPiecesAsync(Message message);
        IList<ChessPiece> GetBlackRemovedChessPieces();
        IList<ChessPiece> GetWhiteRemovedChessPieces();
        string GetMatchScores(bool black);
        Task<IList<Match>> GetMatchesAsync(string loggedInUser);
        Task<Match> GetMatchAsync(int matchId);
        Task UpdateOutcomeAsync(string username, string outcome, int matchId);
        Task<IList<Match>> GetFinishedMatchesAsync(string loggedInUser);
        Task<IList<Move>> GetMovesAsync(int matchId);
        Task<IList<Participant>> GetParticipants(int matchId);

    }
}