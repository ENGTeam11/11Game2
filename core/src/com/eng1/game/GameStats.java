package com.eng1.game;

import com.badlogic.gdx.utils.ObjectMap;

public class GameStats {
    private static int energy = 100; //the energy the player has remaining
    public static final int MAX_ENERGY = 100; // the maximum energy capacity for the player
    private static int score = 0; // the current score of the game
    private static int day, hour, minute; //integer values for the current day and time
    private static final int MINUTES_PER_HOUR = 60;
    private static final int HOURS_PER_DAY = 24;
    private static float elapsedTime = 0; // Time accumulator
    private static ObjectMap<String, Integer> objectives = new ObjectMap<>();// a hashmap containing the count of medals the player has earned
    private static ObjectMap<String, Integer> streaks = new ObjectMap<>(); //a hashmap containing the current daily streak of medals the player has earned
    private static boolean walkedToday = false; //a check for whether the player has entered map 7 that day
    private static String playerName;


    /**
     * initialises the values of the games time to day 1 8am
     */
    public static void initializeGameTime(){
        day = 1; // Start at Day 1
        hour = 8; // Start at 8 AM
        minute = 0;
    }

    /**
     * adds the medal types to the objectives and streaks hashmaps
     */
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

    /**
     * either increases the streak and objective count if the given streak has been completed, or resets the streak count to zero if not
     * @param streak the type of streak being updated
     * @param completed a boolean for whether the streak was completed
     */
    public static void updateStreaks(String streak, boolean completed){
        if (completed){
            objectives.put(streak, objectives.get(streak)+1);
            streaks.put(streak, streaks.get(streak)+1);
        }
        else {
            streaks.put(streak, 0);
        }
    }

    /**
     * returns the current streak for the inputted type
     * @param type A string for the type of streak
     * @return the count of the streak
     */
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

    /**
     * decreases energy by the input amount
     * @param decreaseAmount the energy to remove
     */
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
    
    /**
     * increases the day by 1 and sets time to 8am as well as resetting energy to max
     */
    public static void newDay() {
        day++;
        hour = 8;
        minute = 0;
        energy = MAX_ENERGY;
    }

    /**
     * updates the in game day and time proportional to the real time passed
     * @param delta time since the last render call
     */
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

    /**
     * increases time by an input amount of minutes
     * @param increaseAmount the minutes to increase by
     */
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
