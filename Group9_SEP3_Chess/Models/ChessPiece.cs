using System;

namespace Group9_SEP3_Chess.Models
{
    public class ChessPiece
    {
        private String loaction { get; set; }

        public String getPiece(String type)
        {
            switch (type)
            {
                //Black
                case "BRook":
                    return "Images/BRook.png";
                case "BHorse":
                    return "Images/BHorse.png";
                case  "BBishop": 
                    return "Images/BBishop.png";
                case  "BQueen":
                    return "Images/BQueen.png";
                case  "BKing":
                    return "Images/BKing.png";
                case "BPawn": 
                    return "Images/BPawn.png";
                
                //White
                case "WRook": 
                    return "Images/WRook.png";
                case "WHorse":
                    return "Images/WHorse.png";
                case "WBishop":
                    return "Images/WBishop.png";
                case "WQueen":
                    return "Images/WQueen.png";
                case "WKing":
                    return "Images/WKing.png";
                case "WPawn": 
                    return "Images/WPawn.png";
                default:
                    return null;
            }
        } 
    }
}