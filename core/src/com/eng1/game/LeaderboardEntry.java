package com.eng1.game;

public class LeaderboardEntry {
    private static int score;
    private static String playerName;

    public LeaderboardEntry(String playerName, int score) {
        LeaderboardEntry.playerName = playerName;
        LeaderboardEntry.score = score;
    }

    public static String getPlayerName() {
        return playerName;
    }
    public static void setPlayerName(String playerName) {
        LeaderboardEntry.playerName = playerName;
    }
    public int getScore() {
        return score;
    }
    public static void setScore(int score) {
        LeaderboardEntry.score = score;
    }
}

