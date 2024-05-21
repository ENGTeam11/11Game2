package com.eng1.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LeaderboardManager {
    private static final String LEADERBOARD_FILE = "leaderboard.txt";

    private static File getJarDirectory() {
        try {
            String path = LeaderboardManager.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            return new File(path).getParentFile();
        } catch (Exception e) {
            throw new RuntimeException("Failed to determine JAR directory", e);
        }
    }

    private static FileHandle getLeaderboardFileHandle() {
        File jarDir = getJarDirectory();
        File leaderboardFile = new File(jarDir, LEADERBOARD_FILE);
        return Gdx.files.absolute(leaderboardFile.getAbsolutePath());
    }

    public static void saveScores(List<LeaderboardEntry> entries) {
        FileHandle fileHandle = getLeaderboardFileHandle();
        try (BufferedWriter writer = new BufferedWriter(fileHandle.writer(false))) {
            for (LeaderboardEntry entry : entries) {
                writer.write(entry.getPlayerName() + ":" + entry.getScore() + "\n");
            }
        } catch (IOException e) {
            Gdx.app.error("LeaderboardManager", "Error writing to leaderboard file", e);
        }
    }

    public static List<LeaderboardEntry> loadScores() {
        List<LeaderboardEntry> entries = new ArrayList<>();
        FileHandle fileHandle = getLeaderboardFileHandle();
        if (fileHandle.exists()) {
            try (Scanner scanner = new Scanner(fileHandle.reader())) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        entries.add(new LeaderboardEntry(parts[0], Integer.parseInt(parts[1])));
                    }
                }
            }
        } else {
            Gdx.app.log("LeaderboardManager", "Leaderboard file does not exist.");
        }
        return entries;
    }
}
