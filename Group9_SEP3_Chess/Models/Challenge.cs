using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace Group9_SEP3_Chess.Models
{
    public class Challenge
    {
        [JsonPropertyName("challenger")]
        public string Challenger { get; set; }
        [Required(ErrorMessage = "Enter username")]
        [JsonPropertyName("challenged")]
        public string Username { get; set; }
        [Required(ErrorMessage = "Choose turn length")]
        [JsonPropertyName("turnTime")]
        public int TurnLength { get; set; }

        public override string ToString()
        {
            return $"Challenge. Username: {Username} Challenger: {Challenger} Turn length {TurnLength}";
        }
    }
}