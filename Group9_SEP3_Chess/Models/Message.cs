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
        [JsonPropertyName("firstLayer")]
        public int FirstLayer { get; set; }
        [JsonPropertyName("secondLayer")]
        public int SecondLayer { get; set; }
        [JsonPropertyName("object")]
        public String Object { get; set; }
        [JsonPropertyName("upgradeSelected")]
        public String UpgradeSelected { get; set; }
        public override string ToString()
        {
            return $"Action: {Action} Username: {Username} Email: {Email} Password: {Password}";
        }
    }
}