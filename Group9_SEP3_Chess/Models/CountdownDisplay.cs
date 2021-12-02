using System;

namespace Group9_SEP3_Chess.Models
{
    public class CountdownDisplay
    {
        public int TimeLeft { get; set; }

        public CountdownDisplay(Match match)
        {
            var latestMove = new DateTime(1970, 1, 1, 1, 0, 0, 0, DateTimeKind.Utc).AddMilliseconds(match.LatestMove);
            var difference = (int)(DateTime.Now-latestMove).TotalSeconds;
            TimeLeft = match.TurnTime - difference;
        }

        public void Decrement()
        {
            TimeLeft--;
        }

        public override string ToString()
        {
            var days = TimeLeft / (60 * 60 * 24);
            var hours = TimeLeft % (60 * 60 * 24) / (60 * 60);
            var minutes = TimeLeft % (60*60) / 60;
            var seconds = TimeLeft % 60;
            if (TimeLeft > 0)
            {
                return $"Time left {(days > 0 ? (days > 1 ? $"{days} days " : $"{days} day ") : "")} " +
                       $"{(hours > 0 ? (hours > 1 ? $"{hours} hours " : $"{hours} hour ") : "")} " +
                       $"{(minutes > 0 ? (minutes > 1 ? $"{minutes} minutes " : $"{minutes} minute ") : "")}" +
                       $"{seconds} seconds";
            }
            return "";
        }
    }
}