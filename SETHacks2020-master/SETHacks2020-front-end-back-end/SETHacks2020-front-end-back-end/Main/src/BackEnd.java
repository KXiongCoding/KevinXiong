import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/*
Author: Alex Liu, Michael Cheng
Date: July 18th, 2020
Description: The backend (variable change, synthesizing data, etc) portion of the project
*/

public class BackEnd {

    //variables for speed
    static int speed = 1;

    //variables for fish
    static double food = 0;
    static ArrayList<Fish> fishes = new ArrayList<>();

    //variables for plants
    static int sunlight = 100;
    static ArrayList<Plant> plants = new ArrayList<>();

    //variables for the tank
    static double pH = 7.5;
    static int volume = 1000;
    static int wtemp = 22;
    static int ptemp = 22;

    //Timer
    private static Timer myTimer = new Timer();
    private static long totalElapsedTime = 0;
    private static long elapsedTime = 0;

    //Changes volume of water
    public static void changeVolume(int n)
    {
        volume = n;
    }

    //Increases water temperature by 1 degree Celsius
    public void increaseWTemp()
    {
        wtemp++;
    }

    //Decreases water temperature by 1 degree Celsius
    public void decreaseWTemp()
    {
        wtemp--;
    }

    //Increases ambient plant temperature by 1 degree Celsius
    public void increasePTemp()
    {
        ptemp++;
    }

    //Decreases ambient plant temperature by 1 degree Celsius
    public void decreasePTemp()
    {
        ptemp--;
    }

    public static void setFood(double i)
    {
        food = i;
    }
    
    //Returns the individual amount of food taken in by one fish
    public static double getFood()
    {
        return food / fishes.size();
    }

    //Adds a fish object to the fishes array
    public static void addFish()
    {
        fishes.add(new Fish());
    }

    //Remove a fish object from the fish array
    public static void removeFish(int i)
    {
        fishes.remove(i);
        Fish.removeFish();
    }

    //Adds a plant object to the plants array
    public static void addPlant()
    {
      plants.add(new Plant());
    }

    //Removes a plant object from the plants array
    public static void removePlant(int i)
    {
        plants.remove(i);
        Plant.removePlant();
    }

    //Increases the pH by 0.1
    public static void increasepH()
    {
        pH += 0.1;
    }
    
    //Decreases the pH byb 0.1
    public static void decreasepH()
    {
        pH -= 0.1;
    }
    
    public static void increaseSunlight() {
      sunlight += 25;
    }
    
    public static void decreaseSunlight() {
      sunlight -= 25;
    }
    
    public static Fish getFish(int i) {
        if (fishes.size() > 0)
            return fishes.get(i);
        else
            return null;
    }
    
    public static Plant getPlant(int i) {
        if (plants.size() > 0)
            return plants.get(i);
        else
            return null;
    }
    
    //Start the timer
    public static void startTime() {
        long startTime = System.currentTimeMillis();

        myTimer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                elapsedTime = ((System.currentTimeMillis() - startTime) * speed) + totalElapsedTime;

                ArrayList<Integer> deadFishes = new ArrayList<>();
                ArrayList<Integer> deadPlants = new ArrayList<>();

                //Update fishes array
                if (Fish.getNumFish() > 0)
                {
                    for (Fish fish : fishes)
                    {
                        fish.updateWeight();

                        if (checkDeadFish(fish))
                        {
                            deadFishes.add(fishes.indexOf(fish));
                        }
                    }

                    for (int i = deadFishes.size()-1; i >= 0; i--)
                    {
                        removeFish(i);
                    }
                }

                //Update plants array
                if (Plant.getNumPlant() > 0)
                {
                    for (Plant plant : plants) {
                        plant.updateHealth();

                        if (plant.getHealth().equals("Dead"))
                        {
                            deadPlants.add(plants.indexOf(plant));
                        }
                    }

                    for (int i = deadPlants.size()-1; i >= 0; i--)
                    {
                        removePlant(i);
                    }
                }

                pH += Plant.getQuality()/40;
                Main.getGUI().updateDisplay();
            }
        };

        if (speed != 0)
            myTimer.schedule(task,0,1000/speed);
    }

    //Return the number of elapsed days
    public static int getDays()
    {
        return (int) (elapsedTime/1000);
    }

    //Return the pH
    public static double getpH() {
        return Math.round(pH * 10) / 10.0;
    }

    //Return the amount of sunlight
    public static int getSunlight()
    {
      return sunlight;
    }

    //Checks if a fish has died
    public static boolean checkDeadFish(Fish f)
    {
        if (f.getDeathChance() >= 100)
            return true;

        double chance = (Math.random() * 100) + 50;
        return chance <= f.getDeathChance();
    }

    //Returns the total amount of fish excrement in grams
    public static double getTotalFishOutput()
    {
        int output = 0;

        for (Fish f : fishes)
        {
            output += f.getOutput();
        }

        return output;
    }

    //Returns the water temperature in degrees Celsius
    public static double getWTemp()
    {
        return wtemp;
    }

    //Returns the ambient plant temperature in degrees Celsius
    public static double getPTemp()
    {
        return ptemp;
    }
    //Returns the water temperature in degrees Fahrenheit
    public static double getWTempImperial()
    {
        return (wtemp * 9/5) + 32;
    }

    //Returns the ambient plant temperature in degrees Fahrenheit
    public static double getPTempImperial()
    {
        return (ptemp * 9/5) + 32;
    }

    //Returns the total amount of fish excrement in ounces
    public static double getTotalFishOutputImperial()
    {
        int output = 0;

        for (Fish f : fishes)
        {
            output += f.getOutput();
        }

        return output / 28.35;
    }

    //Pause the timer
    public static void changeSpeed(int s)
    {
        totalElapsedTime = elapsedTime;
        myTimer.cancel();

        if (s != 0)
        {
            speed = s;
            resume();
        }
    }

    //Resume the timer
    public static void resume()
    {
        startTime();
    }
}