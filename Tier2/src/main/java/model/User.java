package model;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * @author group9
 * @version 1.0
 */

public class User
		implements Serializable
{
	private String username;
	private String password;
	private String email;
	private int scorePoints;
	private int wins;
	private int losses;
	private int draws;
	private int gamesPlayed;

	/**
	 * Creating a user
	 *
	 * @param username username
	 * @param password password
	 * @param email    email
	 */
	public User(String username, String password, String email)
	{
		this.username = username;
		this.password = password;
		this.email = email;
	}

	/**
	 * Creating a user
	 *
	 * @param username    username
	 * @param password    password
	 * @param email       email
	 * @param scorePoints score
	 * @param wins        wins
	 * @param losses      losses
	 * @param draws       draws
	 * @param gamesPlayed games played
	 */
	public User(String username, String password, String email, int scorePoints, int wins, int losses, int draws, int gamesPlayed)
	{
		this.username = username;
		this.password = password;
		this.email = email;
		this.scorePoints = scorePoints;
		this.wins = wins;
		this.losses = losses;
		this.draws = draws;
		this.gamesPlayed = gamesPlayed;
	}

	/**
	 * Returns a username
	 *
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * set username
	 *
	 * @param username username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Returns the password
	 *
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set password
	 *
	 * @param password password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Return email
	 *
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email
	 *
	 * @param email email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Return score points
	 *
	 * @return score points
	 */
	public int getScorePoints()
	{
		return scorePoints;
	}

	/**
	 * Sets the score points
	 *
	 * @param scorePoints score points
	 */
	public void setScorePoints(int scorePoints)
	{
		this.scorePoints = scorePoints;
	}

	/**
	 * Returns the wins
	 *
	 * @return wins
	 */
	public int getWins()
	{
		return wins;
	}

	/**
	 * Sets the wins
	 *
	 * @param wins wins
	 */
	public void setWins(int wins)
	{
		this.wins = wins;
	}

	/**
	 * Returns losses
	 *
	 * @return losses
	 */
	public int getLosses()
	{
		return losses;
	}

	/**
	 * Set losses
	 *
	 * @param losses losses
	 */
	public void setLosses(int losses)
	{
		this.losses = losses;
	}

	/**
	 * Return draws
	 *
	 * @return draws
	 */
	public int getDraws()
	{
		return draws;
	}

	/**
	 * Sets the draws
	 *
	 * @param draws draws
	 */
	public void setDraws(int draws)
	{
		this.draws = draws;
	}

	/**
	 * Returns the games played
	 *
	 * @return games played
	 */
	public int getGamesPlayed()
	{
		return gamesPlayed;
	}

	/**
	 * Returns game played
	 *
	 * @param gamesPlayed game played
	 */
	public void setGamesPlayed(int gamesPlayed)
	{
		this.gamesPlayed = gamesPlayed;
	}

	/**
	 * Returns the user as string
	 *
	 * @return user as string
	 */
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
