/*
Author: Alex Liu, Michael Cheng
Date: July 18th, 2020
Description: Plant Objects
*/

public class Plant {
  private static int numPlants = 0;
  private double plantHealth;
  private double plantInput;
  private int plantLifespan = 130;
  private double minFood;
  private int minLight;
  private double qtSunlight;

  public Plant() {
    numPlants++;
    plantHealth = 4.0;
    plantInput = 1.5;
    minFood = ((0.0075/2.205)*1000);
    minLight = 100;
  }

  public void updateHealth() { //update the health of the plant
    double qtFood = BackEnd.getTotalFishOutput() / numPlants;
    qtSunlight = BackEnd.getSunlight() / numPlants;

    //food requirements
    if (qtFood < minFood) {
      if ((qtFood-(minFood/1.2)) < 0) {
        plantHealth += (qtFood-(minFood/1.2));
      } else {
        plantHealth -= (qtFood-(minFood/1.2));
      }
    }

    //sunlight requirements
    if (qtSunlight < minLight) {
      if ((qtSunlight-(minLight/5)) < 0) {
          plantHealth += (qtSunlight-(minLight/5));
      } else {
          plantHealth -= (qtSunlight-(minLight/5));
      }
    }

    if (qtSunlight > 400) { //too much sunlight, plant just dies
      plantHealth = 0;
    }

    //temperature requirements
    if(BackEnd.getPTemp() > 23 || BackEnd.getPTemp() < 21) {
      plantHealth -= 0.625;
    }

    updateLife();

  }

  public String getHealth() { //returning the Health of the Plant
    if (plantHealth <= 0) {
      return "Dead";

    } else if (plantHealth <= 1) {
      return "Weak";

    } else if (plantHealth <= 2) {
      return "Average";

    } else if (plantHealth <= 3) {
      return "Good";

    } else {
      return "Perfect";
    }
  }

  public void updateLife() { //udpating the life expectancy
    if (plantHealth <= 0) {
      plantLifespan -= BackEnd.getDays() * 5;

    } else if (plantHealth <= 1) {
      plantLifespan -= BackEnd.getDays() * 4;

    } else if (plantHealth <= 2) {
      plantLifespan -= BackEnd.getDays() * 3;

    } else if (plantHealth <= 3) {
      plantLifespan -= BackEnd.getDays() * 2;

    } else if (plantHealth <= 4) {
      plantLifespan -= BackEnd.getDays();
    }

  }

  public int getLifeUpdate() {
    return plantLifespan;
  }

  public void setInput() {
    plantInput = BackEnd.getTotalFishOutput() / numPlants;
  }

  public double getQuality() {
    double waterQ = BackEnd.getTotalFishOutput() - (((0.0075/2.205)*1000)*numPlants);

    if (waterQ < 0) {
      return 0;
    } else {
      return waterQ;
    }
  }

  public static void removePlant() {
    numPlants--;
  }

  public int getPlants() {
    return numPlants;
  }
}
