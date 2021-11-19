using System;
using System.Text.Json.Serialization;

namespace Group9_SEP3_Chess.Models
{
    public class Message
    {
        [JsonPropertyName("action")]
        public string Action { get; set; }
        [JsonPropertyName("username")]
        public string Username { get; set; }
        [JsonPropertyName("email")]

        public string Email { get; set; }
        [JsonPropertyName("password")]
        public string Password { get; set; }

        [JsonPropertyName("data")]
        public string Data { get; set; }
        
        public override string ToString()
        {
            return $"Action: {Action} " +
                   $"{(Username==null?"":"Username: "+Username)} " +
                   $"{(Email==null?"":"Email: "+Email)} " +
                   $"{(Password == null ? "":"Password: "+Password)} " +
                   $"{(Data == null ? "": "Data: "+Data)}";
        }
    }
}