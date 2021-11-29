namespace Group9_SEP3_Chess.Models
{
    public class CountdownDisplay
    {
        public int TimeLeft { get; set; }

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
            return $"Time left {(days>0?(days > 1 ? $"{days} Days" : $"{days} Day"):"")} " +
                   $"{(hours>0?(hours > 1 ? $"{hours} Hours" : $"{hours} Hour"):"")} " +
                   $"{(minutes>0?(minutes>1?$"{minutes} Minutes":$"{minutes} Minute"):"")}" +
                   $" {seconds}";
        }
    }
}