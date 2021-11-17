package model;

import java.util.ArrayList;
import java.util.List;

public interface Model {
    String registerUser(String username, String password, String email);
    String validateChallenge(Challenge challenge);
    User validateLogin(String userName, String password);
    ArrayList<Challenge> loadChallenges();
    ArrayList<Challenge> loadChallenges(String username);
    boolean acceptChallenge(Challenge challenge);
    boolean rejectChallenge(Challenge challenge);
}
