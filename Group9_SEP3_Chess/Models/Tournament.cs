using System;
using System.Text.Json.Serialization;
using Microsoft.AspNetCore.Mvc.Formatters;

namespace Group9_SEP3_Chess.Models
{
    public class Tournament
    {
        [JsonPropertyName("creator")]
        public string Creator { get; set; }
        
        [JsonPropertyName("nrOfParticipants")]
        public int NrOfParticipants { get; set; }
        
        [JsonPropertyName("turnTime")]
        public int TurnTime { get; set; }
        
        [JsonPropertyName("tournamentId")]
        public int TournamentId { get; set; }
        
        [JsonPropertyName("top3Players")]
        public string Top3Players { get; set; }
        
        [JsonPropertyName("status")]
        public bool status { get; set; }

       
        
    }
}