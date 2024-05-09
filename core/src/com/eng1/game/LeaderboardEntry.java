package com.eng1.game;

public class LeaderboardEntry {
    private static int score;
    private static String playerName;

    public LeaderboardEntry(String playerName, int score) {
        LeaderboardEntry.playerName = playerName;
        LeaderboardEntry.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }
    public static void setPlayerName(String playerName) {
        LeaderboardEntry.playerName = playerName;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        LeaderboardEntry.score = score;
    }
}

