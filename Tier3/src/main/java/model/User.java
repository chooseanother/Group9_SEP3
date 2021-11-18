package model;/*
 * 12.09.2018 Original version
 */


import com.google.gson.Gson;

import java.io.Serializable;


public class User
		implements Serializable
{
	private String userName;
	private String password;
	private String email;
	private int scorePoints;
	private int wins;
	private int losses;
	private int draws;
	private int gamesPlayed;

	public User(String userName, String password, String email)
	{
		this.userName = userName;
		this.password = password;
		this.email = email;
	}

	public User(String userName, String password, String email, int scorePoints, int wins, int losses, int draws, int gamesPlayed)
	{
		this.userName= userName;
		this.password = password;
		this.email = email;
		this.scorePoints = scorePoints;
		this.wins = wins;
		this.losses = losses;
		this.draws = draws;
		this.gamesPlayed = gamesPlayed;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getScorePoints()
	{
		return scorePoints;
	}

	public void setScorePoints(int scorePoints)
	{
		this.scorePoints = scorePoints;
	}

	public int getWins()
	{
		return wins;
	}

	public void setWins(int wins)
	{
		this.wins = wins;
	}

	public int getLosses()
	{
		return losses;
	}

	public void setLosses(int losses)
	{
		this.losses = losses;
	}

	public int getDraws()
	{
		return draws;
	}

	public void setDraws(int draws)
	{
		this.draws = draws;
	}

	public int getGamesPlayed()
	{
		return gamesPlayed;
	}

	public void setGamesPlayed(int gamesPlayed)
	{
		this.gamesPlayed = gamesPlayed;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
