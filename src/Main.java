/**
 * This program is a simulator that shows how long the main hobo can survive jumping from track to track
 * 
 */

public class Main {

  public static void main (String args[]) {


    // double Svalue, int numTracks, int numTrains, double avgDuration, double avgDelay, int numHobos

    Simulation mySim = new Simulation(10, 10, 100, 35.5, 15.5, 10);

    while (true) {

      if (!mySim.runCycle(1)) { // use 0, 1, or 2 as parameter to indicate which algorithm to use
        
        System.out.println("The End");
        break;
      
      }
    }

    mySim.resetSim();

    while (true) {

      if (!mySim.runCycle(0)) {  // use 0, 1, or 2 as parameter to indicate which algorithm to use
        
        System.out.println("The End");
        break;
      
      }
    }
  }
}
