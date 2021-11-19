using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace Group9_SEP3_Chess.Models
{
    public class User
    {   
        [Required(ErrorMessage = "Username is required")]
        [StringLength(100,ErrorMessage = "Username has to be between 3 and 100 characters",MinimumLength = 3)]
        [JsonPropertyName("username")]
        public string Username { get; set; }
        [Required(ErrorMessage = "Password is required")]
        [StringLength(255, ErrorMessage = "Password must be between 5 and 255 characters", MinimumLength = 5)]
        [DataType(DataType.Password)]
        [JsonPropertyName("password")]

        public string Password { get; set; }
        [Required(ErrorMessage = "Confirm Password is required")]
        [StringLength(255, ErrorMessage = "Confirm Password must be between 5 and 255 characters", MinimumLength = 5)]
        [DataType(DataType.Password)]
        [Compare("Password")]
        [JsonIgnore]
        public string ConfirmPassword { get; set; }
        [Required(ErrorMessage = "Email is required")]
        [DataType(DataType.EmailAddress)]
        [JsonPropertyName("email")]

        public string Email { get; set; }
        [Required(ErrorMessage = "Confirm Email is required")]
        [DataType(DataType.EmailAddress)]
        [Compare("Email")]
        [JsonIgnore]
        public string ConfirmEmail { get; set; }
        [JsonPropertyName("scorePoints")]

        public int ScorePoints { get; set; }
        [JsonPropertyName("wins")]

        public int Wins { get; set; }
        [JsonPropertyName("losses")]

        public int Losses { get; set; }
        [JsonPropertyName("draws")]

        public int Draws { get; set; }
        [JsonPropertyName("gamesPlayed")]

        public int GamesPlayed { get; set; }
      
        public override string ToString()
        {
            return $"Username: {Username} Email: {Email} Password: {Password}";
        }
    }
}