/*
Author: Alex Liu, Michael Cheng
Date: July 18th, 2020
Description: Fish Object
 */

public class Fish
{
    private static int numFish = 0;
    private double weight;
    private int age;
    private double lifespan;
    private double minFood;
    private double output;
    private double deathChance;

    public Fish()
    {
        weight = 2.4;
        age = 0;
        lifespan = 10 * 365;
        minFood = weight * 4.5;
        output = weight * 2.25;
        deathChance = 0;
        numFish++;
    }

    //Update weight based on food intake
    public void updateWeight()
    {
        double diff = BackEnd.getFood() - minFood;
        double diffPercent = (diff / minFood);

        if (diffPercent < 0)
        {
            weight = weight * (1 - (0.6 * Math.abs(diffPercent)));
        }
        else {
            weight = weight * (1.05 + (0.05 * Math.abs(diffPercent)));
        }

        updateMinFood();
        updateOutput();
        updateAge();
        updateDeathChance();
    }

    public void updateMinFood()
    {
        minFood = weight * 4.5;
    }

    public void updateOutput()
    {
        output = BackEnd.getFood() / 2;
    }

    public void updateAge()
    {
        age++;
    }

    public void updateDeathChance()
    {
        double ageDeath = Math.pow(1.005, age - 3463);//4265
        double weightDeath = Math.pow(20, Math.abs(weight - 5.125) - 3.25);
        double phDeath = Math.pow(100, Math.abs(BackEnd.getpH() - 7.5));
        double tempDeath = Math.pow(100, Math.abs(BackEnd.getWTemp() - 22.5) - 1.52);
        double totalChance = ageDeath + weightDeath + phDeath + tempDeath;
        
        deathChance = ageDeath + weightDeath + phDeath + tempDeath;

        deathChance = ageDeath + weightDeath + phDeath;
    }

    public static void removeFish()
    {
        numFish--;
    }
    
    public static int getNumFish()
    {
        return numFish;
    }
    
    public double getMinFood()
    {
        return minFood;
    }

    public double getWeight()
    {
        return weight;
    }

    public int getAge()
    {
        return age;
    }

    public double getLifespan()
    {
        return lifespan;
    }

    public double getOutput()
    {
        return output / 2;
    }

    public double getDeathChance()
    {
        return deathChance;
    }
}
