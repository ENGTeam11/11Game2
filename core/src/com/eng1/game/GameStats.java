package com.eng1.game;

import com.badlogic.gdx.utils.ObjectMap;

public class GameStats {
    private static int energy = 100;
    public static final int MAX_ENERGY = 100;
    private static int score = 0;
    private static int day;
    private static int hour;
    private static int minute;
    private static final int MINUTES_PER_HOUR = 60;
    private static final int HOURS_PER_DAY = 24;
    private static float elapsedTime = 0; // Time accumulator
    private static ObjectMap<String, Integer> objectives = new ObjectMap<>();
    private static ObjectMap<String, Integer> streaks = new ObjectMap<>(); //activity streaks for how long they have been completed for
    private static boolean walkedToday = false;
    private static String playerName;



    public static void initializeGameTime(){
        day = 1; // Start at Day 1
        hour = 8; // Start at 8 AM
        minute = 0;
    }

    public static void initialiseStreaks(){
        objectives.put("Walker", 0);
        objectives.put("Studious", 0);
        objectives.put("Relaxed", 0);
        objectives.put("Well Fed", 0);

        streaks.put("Walker", 0);
        streaks.put("Studious", 0);
        streaks.put("Relaxed", 0);
        streaks.put("Well Fed", 0);
    } 

    public static int getEnergy() {
        return energy;
    }

    public static void setEnergy(int energy) {
        GameStats.energy = energy;
    }


    public static void setWalked(boolean walked){
        walkedToday = walked;
    }

    public static boolean getWalked(){
        return walkedToday;
    }

    public static void updateStreaks(String streak, boolean completed){
        if (completed){
            objectives.put(streak, objectives.get(streak)+1);
            streaks.put(streak, streaks.get(streak)+1);
        }
        else {
            streaks.put(streak, 0);
        }
    }

    public static int getStreak(String type){
        if (type == "Study"){
            return streaks.get("Studious");
        }
        else if (type == "Relax"){
            return streaks.get("Relaxed");
        }
        else if (type == "Eat"){
            return streaks.get("Well Fed");
        }
        else{
            return 0;
        }
    }

    public static void decreaseEnergy(int decreaseAmount) {
        GameStats.energy = Math.max(0, GameStats.energy - decreaseAmount);
    }

    public static int getTime() {
        return hour * 100 + minute;
    }
    public static String getFormattedTime() {
        int hours = getTime() / 100;
        int minutes = getTime() % 100;
        return String.format("%02d:%02d", hours, minutes);
    }
    public static void newDay() {
        day++;
        hour = 8;
        minute = 0;
        energy = MAX_ENERGY;
    }

    public static void initializeGameTimeFlow(float delta) {
        elapsedTime += delta;

        while (elapsedTime >= 1) { // Every second in the real world
            minute++;
            elapsedTime -= 1;

            if (minute >= MINUTES_PER_HOUR) {
                minute = 0;
                hour++;
                if (hour >= HOURS_PER_DAY) {
                    hour = 0;
                    newDay();
                }
            }
        }
    }
    public static void increaseTime(int increaseAmount) {
        minute += increaseAmount;
        while (minute >= MINUTES_PER_HOUR) {
            minute -= MINUTES_PER_HOUR;
            hour++;
            if (hour >= HOURS_PER_DAY) {
                newDay();
            }
        }
    }

    public static ObjectMap<String, Integer> getObjectives(){
        return objectives;
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        GameStats.score = score;
    }

    public static void increaseScore(int score) {
        GameStats.score += score;
    }

    public static int getDay() {
        return day;
    }

    public static void setDay(int newDay) {
        GameStats.day = newDay;
    }
    public static String getPlayerName() {
        return playerName;
    }
    public static void setPlayerName(String playerName) {
        GameStats.playerName = playerName;
    }
}
