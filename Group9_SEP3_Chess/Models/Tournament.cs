using System;
using System.Text.Json.Serialization;
using Microsoft.AspNetCore.Mvc.Formatters;

namespace Group9_SEP3_Chess.Models
{
    public class Tournament
    {
        [JsonPropertyName("creator")]
        public String Creator { get; set; }
        
        [JsonPropertyName("NrOfParticipants")]
        public int NrOfParticipants { get; set; }
        
        [JsonPropertyName("turnTime")]
        public int TurnTime { get; set; }
        
        [JsonPropertyName("tournamentId")]
        public int TournamentId { get; set; }

       
        
    }
}