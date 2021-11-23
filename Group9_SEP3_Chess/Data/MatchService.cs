using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Text;
using System.Text.Json;
using System.Threading;
using System.Threading.Tasks;
using Group9_SEP3_Chess.Json2DArrayHelp;
using Group9_SEP3_Chess.Models;

namespace Group9_SEP3_Chess.Data
{
    public class MatchService:IMatchService
    {
        private IRabbitMQ _rabbitMq;
        private List<ChessPiece> removedChessPieces;
        private String MatchScores;

        public MatchService(IRabbitMQ rabbitMq)
        {
            this._rabbitMq = rabbitMq;
            removedChessPieces = new List<ChessPiece>();
        }
        
        public async Task<ChessPiece> MoveChessPiece(Message message, CancellationToken cancellationToken = default(CancellationToken))
        {
            Message response = await _rabbitMq.SendRequestAsync(message, cancellationToken);
            if (response.Action.Equals("Sending A chess Piece"))
            {
                removedChessPieces = JsonSerializer.Deserialize<List<ChessPiece>>(response.DataSlot2);
                MatchScores = response.DataSlot3;
                ChessPiece chessPiece = JsonSerializer.Deserialize<ChessPiece>(response.Data);
                Console.WriteLine(response.Data);
                Console.WriteLine("Old position"+chessPiece.OldPosition.ToString());
                Console.WriteLine(chessPiece.Selected);
                Console.WriteLine(chessPiece.NewPosition.ToString());
                return chessPiece;
            }
            return null;
            
        }

        public async Task<ChessPiece> UpgradeChessPiece(Message message,CancellationToken cancellationToken = default(CancellationToken))
        {
            Message response = await _rabbitMq.SendRequestAsync(message, cancellationToken);
            if (response.Action.Equals("Upgrade Chess Piece"))
            {
                ChessPiece chessPiece = JsonSerializer.Deserialize<ChessPiece>(response.Data);
                return chessPiece;
            }
            return null;
        }

        public async Task<ChessPiece[,]> LoadChessPieces(Message message, CancellationToken cancellationToken = default(CancellationToken))
        {
            Message response = await _rabbitMq.SendRequestAsync(message, cancellationToken);
            if (response.Action.Equals("Load ChessBoard"))
            { 
                removedChessPieces = JsonSerializer.Deserialize<List<ChessPiece>>(response.DataSlot2);
                MatchScores = response.DataSlot3;
                Console.WriteLine(response.Data);
               ChessPiece[,] chessPieces = JsonSerializer.Deserialize<ChessPiece[,]>(response.Data, new JsonSerializerOptions
               {
                   Converters = { new Array2DConverter() },
               });
                
                return chessPieces;
            }
            return null;
        }

        public IList<ChessPiece> getWhiteRemovedChessPieces()
        {
            List<ChessPiece> whiteChessPieces = new List<ChessPiece>();

            foreach (ChessPiece chessPiece in removedChessPieces)
            {
                if (chessPiece.Color.Equals("White"))
                {
                    whiteChessPieces.Add(chessPiece);
                }   
            }

            return whiteChessPieces;
        }
        
        public IList<ChessPiece> getBlackRemovedChessPieces()
        {
            List<ChessPiece> blackChessPieces = new List<ChessPiece>();

            foreach (ChessPiece chessPiece in removedChessPieces)
            {
                if (chessPiece.Color.Equals("Black"))
                {
                    blackChessPieces.Add(chessPiece);
                }   
            }

            return blackChessPieces;
        }

        public string getMatchScores(bool Black)
        {
            string[] words = MatchScores.Split(" ");
            if (Black)
            {
                return words[0];
            }
            else
            {
                return words[1];
            }
            
        }
    }
    }
