package model;

import java.util.ArrayList;

public class Matches {
    private ArrayList<Match> matches;

    public Matches() {
        matches = new ArrayList<>();
    }

    public Match getMatch(int matchID){
        for (Match m : matches){
            if (m.getMatchID() == matchID)
                return m;
        }
        return null;
    }

    public void addMatch(Match match){
        matches.add(match);
    }

    public ArrayList<Match> getMatches() {
        return matches;
    }

    public void setMatches(ArrayList<Match> matches) {
        this.matches = matches;
    }
}
