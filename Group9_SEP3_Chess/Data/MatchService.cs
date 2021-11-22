using System;
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

        public MatchService(IRabbitMQ _rabbitMq)
        {
            this._rabbitMq = _rabbitMq;
        }
        public async Task<ChessPiece> MoveChessPiece(Message message, CancellationToken cancellationToken = default(CancellationToken))
        {
            Message response = await _rabbitMq.SendRequestAsync(message, cancellationToken);
            if (response.Action.Equals("Sending A chess Piece"))
            {
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
                Console.WriteLine(response.Data);
               ChessPiece[,] chessPieces = JsonSerializer.Deserialize<ChessPiece[,]>(response.Data, new JsonSerializerOptions
               {
                   Converters = { new Array2DConverter() },
               });
                
                return chessPieces;
            }
            return null;
        }
        }
    }
