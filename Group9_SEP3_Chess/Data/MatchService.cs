using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Text;
using System.Text.Json;
using System.Text.RegularExpressions;
using System.Threading;
using System.Threading.Tasks;
using Group9_SEP3_Chess.Json2DArrayHelp;
using Group9_SEP3_Chess.Models;
using Match = Group9_SEP3_Chess.Models.Match;

namespace Group9_SEP3_Chess.Data
{
    public class MatchService : IMatchService
    {
        private IRabbitMQ _rabbitMq;
        private List<ChessPiece> removedChessPieces;
        private string MatchScores;
        private JsonSerializerOptions jsonOptions;

        public MatchService(IRabbitMQ rabbitMq)
        {
            this._rabbitMq = rabbitMq;
            removedChessPieces = new List<ChessPiece>();
            jsonOptions = new JsonSerializerOptions {PropertyNamingPolicy = JsonNamingPolicy.CamelCase};
        }

        public async Task<ChessPiece> MoveChessPiece(Message message,
            CancellationToken cancellationToken = default(CancellationToken))
        {
            Message response = await _rabbitMq.SendRequestAsync(message, cancellationToken);
            if (response.Action.Equals("Sending A chess Piece"))
            {
                removedChessPieces = JsonSerializer.Deserialize<List<ChessPiece>>(response.DataSlot2);
                MatchScores = response.DataSlot3;
                ChessPiece chessPiece = JsonSerializer.Deserialize<ChessPiece>(response.Data);
                /*Console.WriteLine(response.Data);
                Console.WriteLine("Old position" + chessPiece.OldPosition.ToString());
                Console.WriteLine(chessPiece.Selected);
                Console.WriteLine(chessPiece.NewPosition.ToString());*/
                return chessPiece;
            }

            return null;

        }

        public async Task<ChessPiece> UpgradeChessPiece(Message message,
            CancellationToken cancellationToken = default(CancellationToken))
        {
            Message response = await _rabbitMq.SendRequestAsync(message, cancellationToken);
            if (response.Action.Equals("Upgrade Chess Piece"))
            {
                ChessPiece chessPiece = JsonSerializer.Deserialize<ChessPiece>(response.Data);
                return chessPiece;
            }

            return null;
        }

        public async Task<ChessPiece[,]> LoadChessPieces(Message message,
            CancellationToken cancellationToken = default(CancellationToken))
        {
            Message response = await _rabbitMq.SendRequestAsync(message, cancellationToken);
            if (response.Action.Equals("Load ChessBoard"))
            {
                removedChessPieces = JsonSerializer.Deserialize<List<ChessPiece>>(response.DataSlot2);
                MatchScores = response.DataSlot3;
                ChessPiece[,] chessPieces = JsonSerializer.Deserialize<ChessPiece[,]>(response.Data,
                    new JsonSerializerOptions
                    {
                        Converters = {new Array2DConverter()},
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
            Console.WriteLine(MatchScores);
            if (Black)
            {
                return words[1];
            }
            else
            {
                return words[0];
            }

        }

        public async Task<IList<Match>> GetMatches(string loggedInUser)
        {
            Message response = await _rabbitMq.SendRequestAsync(new Message
            {
                Action = "GetMatches",
                Data = JsonSerializer.Serialize(loggedInUser)
            });
            Console.WriteLine(response);
            if (response.Action.Equals("Matches"))
            {
                IList<Match> rm = JsonSerializer.Deserialize<IList<Match>>(response.Data, jsonOptions);
              return rm;
            }
            else
            {
                throw new Exception($"{response.Action}");
            }
        }

        public async Task UpdateOutcome(string username, string outcome, int matchId)
        {
            var response = await _rabbitMq.SendRequestAsync(new Message
            {
                Action = "UpdateOutcome",
                Data = username,
                DataSlot2 = outcome,
                DataSlot3 = ""+matchId
            });
        }

        public async Task<IList<Match>> GetFinishedMatches(string loggedInUser)
        {
            Message response = await _rabbitMq.SendRequestAsync(new Message
            {
                Action = "GetMatchHistory",
                Data = JsonSerializer.Serialize(loggedInUser)
            });
            if (response.Action.Equals("MatchHistory"))
            {
                IList<Match> rm = JsonSerializer.Deserialize<IList<Match>>(response.Data, jsonOptions);
                return rm;
            }
            else
            {
                throw new Exception($"{response.Action}");
            }
        }

        public async Task<Match> GetMatch(int matchId)
        {
            var response = await _rabbitMq.SendRequestAsync(new Message
            {
                Action = "GetMatch",
                Data = ""+matchId
            });
            if (response.Action.Equals("Match"))
            {
                return JsonSerializer.Deserialize<Match>(response.Data, jsonOptions);
            }
            else
            {
                throw new Exception($"{response.Action}");
            }
            
        }

        public async Task<IList<Move>> GetMoves(int matchId)
        {
            Message response = await _rabbitMq.SendRequestAsync(new Message
            {
                Action = "MoveHistory",
                Data = JsonSerializer.Serialize(matchId)
            });
            if (response.Action.Equals("HistoryOfMoves"))
            {
                IList<Move> Rm = JsonSerializer.Deserialize<IList<Move>>(response.Data, new JsonSerializerOptions
                {
                    PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
                });
                IList<string> Letters = new List<string>
                {
                    "A", "B", "C", "D", "E", "F", "G", "H"
                };
                IList<string> Numbers = new List<string>
                {
                    "8", "7", "6", "5", "4", "3", "2", "1" 
                };
                foreach (Move m in Rm)
                {
                    string[] Start = m.StartPosition.Split(":");
                    string[] End = m.EndPosition.Split(":");
                    m.StartPosition = Letters[int.Parse(Start[1])] + ": " + Numbers[int.Parse(Start[0])];
                    m.EndPosition = Letters[int.Parse(End[1])] + ": " + Numbers[int.Parse(End[0])];
                }
                return Rm;
            }
            else
            {
                throw new Exception($"{response.Action}");
            }
        }
    }
}
    
