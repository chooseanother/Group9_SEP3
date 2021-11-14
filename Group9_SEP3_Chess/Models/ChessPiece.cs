using System;
using System.Text.Json.Serialization;

namespace Group9_SEP3_Chess.Models
{
    public class ChessPiece
    {
        [JsonPropertyName("type")]
        public String Type { get; set; }
        [JsonPropertyName("selected")]
        public bool Selected { get; set; }
        [JsonPropertyName("oldPosition")]
        public Position OldPosition { get; set; }
        [JsonPropertyName("newPosition")]
        public Position NewPosition { get; set; }

        public ChessPiece()
        {
        }
        public String GetPiece()
        {
            switch (Type.ToLower())
            {
                //Black
                case "black-rook":
                    return "Images/BRook.png";
                case "black-horse":
                    return "Images/BHorse.png";
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
                case "white-horse":
                    return "Images/WHorse.png";
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