
import java.util.*;

class Simulation {

  // keeps track of the state of the simulation, simulation is played through Main.java
  
  private int cycleNum;
  
  private int mainHealth = 200;

  private double Svalue;
  
  private ArrayList<Boolean> tracks; // change to arraylist?
  
  private double avgDuration;
  
  private double avgDelay;
  
  private int numHobos;

  public Simulation (double Svalue, int numTracks, double avgDuration, double avgDelay, int numHobos) {
  
    ArrayList<Boolean> tracks = new ArrayList<Boolean>();
    
    
    for (int i = 0; i < numTracks; i++) {
      tracks.add(false);
    }
    
    this.cycleNum = 0;
    this.tracks = tracks;
    this.Svalue = Svalue;
    this.avgDuration = avgDuration;
    this.avgDelay = avgDelay;
    
    
  
  
  }
  
  // this function will make the calculations, move the simulation one cycle forward
  public void runCycle () {
    
    
  
  }



}


