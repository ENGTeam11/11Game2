package com.eng1.game;

public class LeaderboardEntry {
    private String playerName;
    private int score;

    /**
     * sets the playerName and score to the input values
     * @param playerName the name of the player
     * @param score the score the player achieved
     */
    public LeaderboardEntry(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }
    public int getScore() {
        return score;
    }

}
