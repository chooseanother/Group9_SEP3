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
        
        public override string ToString()
        {
            return $"Action: {Action} Username: {Username} Email: {Email} Password: {Password}";
        }
    }
}