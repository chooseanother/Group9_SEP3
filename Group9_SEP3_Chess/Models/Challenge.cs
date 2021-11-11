using System.ComponentModel.DataAnnotations;

namespace Group9_SEP3_Chess.Models
{
    public class Challenge
    {
        [Required(ErrorMessage = "Choose turn length")]
        public string TurnLength { get; set; }
        [Required(ErrorMessage = "Enter username")]
        public string Username { get; set; }
        public string Challenger { get; set; }
    }
}