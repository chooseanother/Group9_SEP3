﻿using System;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using Group9_SEP3_Chess.Models;

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
    }
}