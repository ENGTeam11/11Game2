package com.eng1.game;

import com.badlogic.gdx.Gdx;


import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LeaderboardManager {
    private static final String LEADERBOARD_FILE = "leaderboard.txt";

    public static void saveScores(List<LeaderboardEntry> entries) {
        try (BufferedWriter writer = new BufferedWriter(Gdx.files.local(LEADERBOARD_FILE).writer(false))) {
            for (LeaderboardEntry entry : entries) {
                writer.write(entry.getPlayerName() + ":" + entry.getScore() + "\n");
            }
        } catch (IOException e) {
            Gdx.app.error("LeaderboardManager", "Error writing to leaderboard file", e);
        }
    }

    public static List<LeaderboardEntry> loadScores() {
        List<LeaderboardEntry> entries = new ArrayList<>();
        if (Gdx.files.local(LEADERBOARD_FILE).exists()) {
            try (Scanner scanner = new Scanner(Gdx.files.local(LEADERBOARD_FILE).reader())) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        entries.add(new LeaderboardEntry(parts[0], Integer.parseInt(parts[1])));
                    }
                }
            }
        }
        return entries;
    }
}
