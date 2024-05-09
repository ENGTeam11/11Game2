package com.eng1.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Interpolation;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

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
        activities.get("Study").put("CompSci", new Activity(180, 30, 0));

        // Relax: Gym
        activities.get("Relax").put("Gym", new Activity(60, 20, 0));
        activities.get("Relax").put("Map1", new Activity(60, 10, 0));
        activities.get("Relax").put("Basketball", new Activity(60, 250, 0));
        activities.get("Relax").put("Football", new Activity(90, 30, 0));

        // Eat: Piazza Building
        activities.get("Eat").put("Piazza", new Activity(60, 10, 0));

        // Sleep: Home
        activities.get("Sleep").put("Home", new Activity(0,0, 0));
    }

    public static void completeActivity(String activityIdentifier) {
<<<<<<< HEAD
        String[] parts = activityIdentifier.split(",");
        String type = parts[0];
        String location = parts[1];

        Activity activity = activities.get(type).get(location);
        System.out.println(activity.complete());

=======
        // Takes a string to indicate the activity being completed. i.e. "Relax,Gym"
        String[] activityLocator = activityIdentifier.split(",");
        String type = activityLocator[0];
        String location = activityLocator[1];
        System.out.println(type);
        System.out.println(location);
        System.out.println(activities.get(type).get(location).complete());
        if(type.equals("Eat")  ){
            gameInstance.changeScreen(MenuState.FOODNINJA);
        }
        if(type.equals("Study")){
            gameInstance.changeScreen(MenuState.ACADEMICWEAPON);
        }
>>>>>>> Minigames
        if (type.equals("Sleep")) {
            sleep();
        }
    }

    public String complete(){
        if (GameStats.getEnergy() < energyNeeded) {
            return "Insufficient energy";
        }
        GameStats.increaseTime(timeNeeded);
        if (GameStats.getTime() > 2400) {
            return "Insufficient time";
        }
<<<<<<< HEAD
=======
        

        // Increase the relevent trackers
>>>>>>> Minigames
        this.timesCompletedDay++;
        this.timesCompletedWeek++;
        GameStats.decreaseEnergy(this.energyNeeded);
        GameStats.increaseScore(reward);

        return "Activity Completed";

        // Debugging
        // ---
        //System.out.println("Current time: " + GameStats.getTime());
        //System.out.println("Current energy: " + GameStats.getEnergy());
        //System.out.println("Current score: " + GameStats.getScore());
        // ---

    }



    public static void sleep() {
        // Debugging
        // ---
        // System.out.println("Sleeping");
        // ---

        GameStats.newDay();

        resetDayActivities();
        if (GameStats.getDay() > 7) {
<<<<<<< HEAD
            gameInstance.changeScreen(HeslingtonHustle.ENDGAME);
=======
            // Pass the score and activities completed to EndScreen
            Map<String, Integer> activitiesCompleted = Activity.countCompletedActivities();
            int score = calculateDayScore();
            gameInstance.changeScreen(MenuState.ENDGAME);
>>>>>>> Minigames
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
