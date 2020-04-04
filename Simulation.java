
import java.util.*;

class Simulation {

  // keeps track of the state of the simulation, simulation is played through Main.java
  
  private int cycleNum;
  
  private int mainHealth = 200;

  private double Svalue;
  
  private ArrayList<Track> tracks; // Track objects instead of boolean
  
  private ArrayList<Hobo> hobos;

  /*
  private double avgDuration;
  
  private double avgDelay;
  
  private int numHobos;

  private int numTrains;

  */

  public Simulation (double Svalue, int numTracks, int numTrains, double avgDuration, double avgDelay, int numHobos) {
  
    
    for (int i = 0; i < numTracks; i++) {
      this.tracks.add(new Track(i, avgDuration, avgDelay, numTrains));
    }

    for (int i = 0; i < numHobos; i++) {
      this.hobos.add(new Hobo(i));
    }
    
    this.cycleNum = 0;
    this.Svalue = Svalue;
    
    
  }
  
  // this function will make the calculations, move the simulation one cycle forward
  public void runCycle () {
    
    
  
  }

  public void generateMessages () {
    // if chance to send message
    // 
  }

  // sets the safety value for each track for the given time
  public void setSafety (double currentTime) {

    
  }



}


