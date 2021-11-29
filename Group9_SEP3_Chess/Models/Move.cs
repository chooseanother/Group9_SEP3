using System.Text.Json.Serialization;

namespace Group9_SEP3_Chess.Models
{
    public class Move
    {
        [JsonPropertyName("matchId")]
        public int MatchID { get; set; }
        [JsonPropertyName("moveId")]
        public int MoveID { get; set; }
        [JsonPropertyName("piece")]
        public string Piece { get; set; }
        [JsonPropertyName("color")]
        public string Color { get; set; }
        [JsonPropertyName("startPosition")]
        public string StartPosition { get; set; }
        [JsonPropertyName("endPosition")]
        public string EndPosition { get; set; }
    }
}