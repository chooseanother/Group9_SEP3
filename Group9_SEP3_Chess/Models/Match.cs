using System;
using System.Text.Json.Serialization;
using Microsoft.VisualBasic;

namespace Group9_SEP3_Chess.Models
{
    public class Match
    {
        [JsonPropertyName("matchID")]
        public int MatchId { get; set; }
        [JsonPropertyName("tournamentID")]
        public  int TournamentId { get; set; }
        [JsonPropertyName("turnTime")]
        public  int TurnTime { get; set; }
        [JsonPropertyName("type")]
        public  string Type { get; set; }
        [JsonPropertyName("finished")]
        public  bool Finished { get; set; }
        [JsonPropertyName("usersTurn")]
        public  string UsersTurn { get; set; }
        [JsonPropertyName("latestMove")]
        public  long LatestMove { get; set; }
        [JsonPropertyName("whitePlayer")]
        public  Participant WhitePlayer { get; set; }
        [JsonPropertyName("blackPlayer")]
        public  Participant BlackPlayer { get; set; }

    }
}
