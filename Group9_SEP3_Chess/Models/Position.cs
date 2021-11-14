using System.Runtime.CompilerServices;
using System.Text.Json.Serialization;

namespace Group9_SEP3_Chess.Models
{
    public class Position
    {
        [JsonPropertyName("verticalAxis")]
        public int VerticalAxis { get; set; }
        [JsonPropertyName("horizontalAxis")]
        public int HorizontalAxis { get; set; }

        public Position()
        {
            VerticalAxis = 0;
            HorizontalAxis = 0;
        }

        public override string ToString()
        {
            return $"Coordinates (Y,X): {VerticalAxis},{HorizontalAxis}";
        }
    }
}