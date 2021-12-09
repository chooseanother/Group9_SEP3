using System.Collections.Generic;

namespace Group9_SEP3_Chess.Models
{
    public class TurnLengths
    {
        public IDictionary<int, string> TurnTimes { get; set; } = new Dictionary<int, string>
        {
            {120, "2 Minutes"}, {1800, "30 Minutes"}, {3600, "1 Hour"}, {7200, "2 Hours"}, 
            {36000, "10 Hours"}, {86400, "1 Day"}, {259200, "3 Days"}, {604800, "7 Days"}
        };
    }
}