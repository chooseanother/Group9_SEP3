using System.Text.Json.Serialization;

namespace Group9_SEP3_Chess.Models
{
    public class Participant
    {
        [JsonPropertyName("username")]
        public string Username { get; set; }
        [JsonPropertyName("matchId")]
        public int MatchId { get; set; }
        [JsonPropertyName("color")]
        public string Color { get; set; }
        [JsonPropertyName("outcome")]
        public string Outcome { get; set; }


    }
}