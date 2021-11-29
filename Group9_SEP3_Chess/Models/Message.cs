using System;
using System.Text.Json.Serialization;

namespace Group9_SEP3_Chess.Models
{
    public class Message
    {
        [JsonPropertyName("action")]
        public string Action { get; set; }
        [JsonPropertyName("data")]
        public string Data { get; set; }
        
        [JsonPropertyName("dataSlot2")]
        public string DataSlot2 { get; set; }
        
        [JsonPropertyName("dataSlot3")]
        public string DataSlot3 { get; set; }
        
        public override string ToString()
        {
            return $"Action: {Action} " +
                   $"{(Data == null ? "": "Data: "+Data)}"+
                   $"{(DataSlot2 == null ? "": "Data2: "+DataSlot2)}"+
                   $"{(DataSlot3 == null ? "": "Data3: "+DataSlot3)}";
        }
    }
}