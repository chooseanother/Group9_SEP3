using System;
using System.Runtime.Serialization;
using System.Text.Json.Serialization;

namespace Group9_SEP3_Chess.Models
{
    public class ChessPiece
    {
        [JsonPropertyName("type")]
        public string Type { get; set; }
        [JsonPropertyName("selected")]
        public bool Selected { get; set; }
        [JsonPropertyName("oldPosition")]
        public Position OldPosition { get; set; }
        [JsonPropertyName("newPosition")]
        public Position NewPosition { get; set; }
        [JsonPropertyName("color")]
        public string Color { get; set; }

        public string GetPiece()
        {
            switch (Color.ToLower()+"-"+Type.ToLower())
            {
                //Black
                case "black-rook":
                    return "Images/BRook.png";
                case "black-knight":
                    return "Images/BKnight.png";
                case  "black-bishop": 
                    return "Images/BBishop.png";
                case  "black-queen":
                    return "Images/BQueen.png";
                case  "black-king":
                    return "Images/BKing.png";
                case "black-pawn": 
                    return "Images/BPawn.png";
                
                //White
                case "white-rook": 
                    return "Images/WRook.png";
                case "white-knight":
                    return "Images/WKnight.png";
                case "white-bishop":
                    return "Images/WBishop.png";
                case "white-queen":
                    return "Images/WQueen.png";
                case "white-king":
                    return "Images/WKing.png";
                case "white-pawn": 
                    return "Images/WPawn.png";
                default:
                    return "none";
            }
        }
    }
}