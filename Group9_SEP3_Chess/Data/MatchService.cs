using System;
using System.Collections.Generic;
using System.Text.Json;
using System.Threading.Tasks;
using Group9_SEP3_Chess.Json2DArrayHelp;
using Group9_SEP3_Chess.Models;

namespace Group9_SEP3_Chess.Data
{
    public class MatchService : IMatchService
    {
        private readonly IRabbitMQ rabbitMq;
        private List<ChessPiece> removedChessPieces;
        private string matchScores;
        private readonly JsonSerializerOptions jsonOptions;

        public MatchService(IRabbitMQ rabbitMq)
        {
            this.rabbitMq = rabbitMq;
            removedChessPieces = new List<ChessPiece>();
            jsonOptions = new JsonSerializerOptions {PropertyNamingPolicy = JsonNamingPolicy.CamelCase};
        }

        public async Task<ChessPiece> MoveChessPiece(Message message)
        {
            Message response = await rabbitMq.SendRequestAsync(message);
            if (response.Action.Equals("Sending A chess Piece"))
            {
                removedChessPieces = JsonSerializer.Deserialize<List<ChessPiece>>(response.DataSlot2);
                matchScores = response.DataSlot3;
                ChessPiece chessPiece = JsonSerializer.Deserialize<ChessPiece>(response.Data);
                /*Console.WriteLine(response.Data);
                Console.WriteLine("Old position" + chessPiece.OldPosition.ToString());
                Console.WriteLine(chessPiece.Selected);
                Console.WriteLine(chessPiece.NewPosition.ToString());*/
                return chessPiece;
            }

            return null;

        }

        public async Task<ChessPiece> UpgradeChessPiece(Message message)
        {
            Message response = await rabbitMq.SendRequestAsync(message);
            if (response.Action.Equals("Upgrade Chess Piece"))
            {
                ChessPiece chessPiece = JsonSerializer.Deserialize<ChessPiece>(response.Data);
                return chessPiece;
            }

            return null;
        }

        public async Task<ChessPiece[,]> LoadChessPieces(Message message)
        {
            Message response = await rabbitMq.SendRequestAsync(message);
            if (response.Action.Equals("Load ChessBoard"))
            {
                removedChessPieces = JsonSerializer.Deserialize<List<ChessPiece>>(response.DataSlot2);
                matchScores = response.DataSlot3;
                ChessPiece[,] chessPieces = JsonSerializer.Deserialize<ChessPiece[,]>(response.Data,
                    new JsonSerializerOptions
                    {
                        Converters = {new Array2DConverter()},
                    });

                return chessPieces;
            }

            return null;
        }

        public IList<ChessPiece> GetWhiteRemovedChessPieces()
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

        public IList<ChessPiece> GetBlackRemovedChessPieces()
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

        public string GetMatchScores(bool black)
        {
            string[] words = matchScores.Split(" ");
            return black ? words[1] : words[0];
        }

        public async Task<IList<Match>> GetMatches(string loggedInUser)
        {
            Message response = await rabbitMq.SendRequestAsync(new Message
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
            var response = await rabbitMq.SendRequestAsync(new Message
            {
                Action = "UpdateOutcome",
                Data = username,
                DataSlot2 = outcome,
                DataSlot3 = ""+matchId
            });
        }

        public async Task<IList<Match>> GetFinishedMatches(string loggedInUser)
        {
            Message response = await rabbitMq.SendRequestAsync(new Message
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
            var response = await rabbitMq.SendRequestAsync(new Message
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
            Message response = await rabbitMq.SendRequestAsync(new Message
            {
                Action = "MoveHistory",
                Data = JsonSerializer.Serialize(matchId)
            });
            if (response.Action.Equals("HistoryOfMoves"))
            {
                var moves = JsonSerializer.Deserialize<IList<Move>>(response.Data, new JsonSerializerOptions
                {
                    PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
                });
                IList<string> letters = new List<string>
                {
                    "A", "B", "C", "D", "E", "F", "G", "H"
                };
                IList<string> numbers = new List<string>
                {
                    "8", "7", "6", "5", "4", "3", "2", "1" 
                };
                if (moves == null)
                {
                    return moves;
                }
                foreach (var m in moves)
                {
                    var start = m.StartPosition.Split(":");
                    var end = m.EndPosition.Split(":");
                    m.StartPosition = letters[int.Parse(start[1])] + ": " + numbers[int.Parse(start[0])];
                    m.EndPosition = letters[int.Parse(end[1])] + ": " + numbers[int.Parse(end[0])];
                }
                return moves;
            }
            else
            {
                throw new Exception($"{response.Action}");
            }
        }
    }
}
    
