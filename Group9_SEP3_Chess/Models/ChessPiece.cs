using System;

namespace Group9_SEP3_Chess.Models
{
    public class ChessPiece
    {
        public String Type { get; set; }
        public bool Selected { get; set; }
        public String GetPiece()
        {
            switch (Type.ToLower())
            {
                //Black
                case "brook":
                    return "Images/BRook.png";
                case "bhorse":
                    return "Images/BHorse.png";
                case  "bbishop": 
                    return "Images/BBishop.png";
                case  "bqueen":
                    return "Images/BQueen.png";
                case  "bking":
                    return "Images/BKing.png";
                case "bpawn": 
                    return "Images/BPawn.png";
                
                //White
                case "wrook": 
                    return "Images/WRook.png";
                case "whorse":
                    return "Images/WHorse.png";
                case "wbishop":
                    return "Images/WBishop.png";
                case "wqueen":
                    return "Images/WQueen.png";
                case "wking":
                    return "Images/WKing.png";
                case "wpawn": 
                    return "Images/WPawn.png";
                default:
                    return "none";
            }
        } 
    }
}