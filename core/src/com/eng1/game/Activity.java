package com.eng1.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Interpolation;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.eng1.game.HeslingtonHustle;


public class Activity {

    private static Map<String, Map<String, Activity>> activities; // Map of the activity types
    // each activity type containing a map of the locations and activities within those locations
    // Activities can be called using the type then the location
    private final int timeNeeded; // Time required to complete the activity
    private final int energyNeeded; // Energy required to complete the activity
    private int timesCompletedWeek; // Times the activity has been completed in the week (can be used to decrese the reward of an activity if it is completed multiple times)
    private int timesCompletedDay; // Times the activity has been completed that day (can be used to stop activites being completed too many times or to increase the reward i.e. eating 3 meals)
    private final int reward; // Score the activity gives for being completed

    private static HeslingtonHustle gameInstance; // Reference to the HeslingtonHustle instance

    private static int finalScore;

    public Activity(int timeNeeded, int energyNeeded, int reward) {
        // Constructor for activities
        this.timeNeeded = timeNeeded;
        this.energyNeeded = energyNeeded;
        this.timesCompletedWeek = 0;
        this.timesCompletedDay = 0;
        this.reward = reward;
    }

    // Method to set the game instance
    public static void setGameInstance(HeslingtonHustle game) {
        gameInstance = game;
    }

    public static void createActivities() {
        // Create all activities here
        // Call this method at the start of a new game

        activities = new HashMap<>();
        // Add in each type of activity
        activities.put("Study", new HashMap<>());
        activities.put("Relax", new HashMap<>());
        activities.put("Eat", new HashMap<>());
        activities.put("Sleep", new HashMap<>());

        // Add activties to their activity type
        // Study: CompSci Building
        activities.get("Study").put("CompSci", new Activity(180, 30, 20));

        // Relax: Gym
        activities.get("Relax").put("Gym", new Activity(60, 20, 10));
        activities.get("Relax").put("Map1", new Activity(60, 10, 3));
        activities.get("Relax").put("Basketball", new Activity(60, 25, 15));
        activities.get("Relax").put("Football", new Activity(90, 30, 17));

        // Eat: Piazza Building
        activities.get("Eat").put("Piazza", new Activity(60, 10, 13));

        // Sleep: Home
        activities.get("Sleep").put("Home", new Activity(0,0, 0));
    }

    public static void completeActivity(String activityIdentifier) {
        String[] parts = activityIdentifier.split(",");
        String type = parts[0];
        String location = parts[1];

        Activity activity = activities.get(type).get(location);
        activity.complete(type);
        if(type.equals("Eat")  ){
            gameInstance.changeScreen(MenuState.FOODNINJA);
        }
        if(type.equals("Study")){
            gameInstance.changeScreen(MenuState.ACADEMICWEAPON);
        }
        if (type.equals("Relax")) {
            gameInstance.changeScreen(MenuState.BASKETBALL);
        }
        if (type.equals("Sleep")) {
            sleep();
        }
    }

    public String complete(String type){
        if (GameStats.getEnergy() < energyNeeded) {
            return "Insufficient energy";
        }
        GameStats.increaseTime(timeNeeded);
        if (GameStats.getTime() > 2400) {
            return "Insufficient time";
        }
        this.timesCompletedDay++;
        this.timesCompletedWeek++;
        GameStats.decreaseEnergy(this.energyNeeded);
        int score = Math.round((float)(reward * (1 + 0.2*GameStats.getStreak(type))));
        if(GameStats.getWalked()){
            score += 1;
        }
        GameStats.increaseScore(score);

        return "Activity Completed";

        // Debugging
        // ---
        //System.out.println("Current time: " + GameStats.getTime());
        //System.out.println("Current energy: " + GameStats.getEnergy());
        //System.out.println("Current score: " + GameStats.getScore());
        // ---

    }


    public static int getTotalComplete(String activityType){
        int total = 0;
        for (Entry<String, Activity> set : activities.get(activityType).entrySet()) {
            total += set.getValue().timesCompletedDay;
        }
        return total;
    }

    public static void sleep() {
        // Debugging
        // ---
        // System.out.println("Sleeping");
        // ---

        GameStats.newDay();
        
        boolean studious = false;
        boolean wellFed = false;
        boolean relaxed = false;
        int totalRelax = getTotalComplete("Relax");
        int totalStudy = getTotalComplete("Study");
        int totalEat = getTotalComplete("Eat");

        if (totalStudy >= 2) {studious = true;}
        if (totalRelax >= 2) {relaxed = true;}
        if (totalEat >= 3) {wellFed = true;}

        GameStats.updateStreaks("Relaxed", relaxed);
        GameStats.updateStreaks("Studious", studious);
        GameStats.updateStreaks("Well Fed", wellFed);
        GameStats.updateStreaks("Walker", GameStats.getWalked());
        GameStats.setWalked(false);

        resetDayActivities();
        if (GameStats.getDay() > 7) {
            // Pass the score and activities completed to EndScreen
            Map<String, Integer> activitiesCompleted = Activity.countCompletedActivities();
            int score = calculateDayScore();
            setFinalScore(GameStats.getScore());
            gameInstance.changeScreen(MenuState.ENDGAME);

            
        }


    }

    public static Map<String, Integer> countCompletedActivities() {
        // Count the completed activites for the end screen
        Map<String, Integer> counts = new HashMap<>();
        // Add in each type of activity
        counts.put("Study", 0);
        counts.put("Relax", 0);
        counts.put("Eat", 0);
        counts.put("Sleep", 0);
        for (String type : activities.keySet()) {
            // typeActivities is the map for each type of activity
            for (Activity activity : activities.get(type).values()) {
                counts.put(type, counts.get(type) + activity.timesCompletedWeek);
            }
        }

        return counts;
    }

    public static int calculateDayScore() {
        // Score to be calculated here
        // Can be completed by iterating through the activites and checking if their timeCompletedDay is > 0
        return  0;
    }

    public static int getFinalScore() {
        return finalScore;
    }

    public static void setFinalScore(int score) {
        finalScore = score;
    }

    private static void resetDayActivities() {
        for (Map<String, Activity> typeActivities : activities.values()) {
            for (Activity activity : typeActivities.values()) {
                activity.timesCompletedDay = 0;
            }
        }
    }
}
