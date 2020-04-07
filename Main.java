import java.util.concurrent.TimeUnit;

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
      
      try {
        
        //TimeUnit.MILLISECONDS.sleep(1000); // sleep for 1 second to make the simulation slower
        //TimeUnit.MILLISECONDS.sleep(500); // 500 milliseconds is half a second  

      } catch (Exception e) {  
        e.printStackTrace();
      }
      
    
    }

    mySim.resetSim();

    while (true) {

      if (!mySim.runCycle(0)) {  // use 0, 1, or 2 as parameter to indicate which algorithm to use
        
        System.out.println("The End");
        break;
      
      }
      
      try {
        
        //TimeUnit.MILLISECONDS.sleep(1000); // sleep for 1 second to make the simulation slower
        //TimeUnit.MILLISECONDS.sleep(500); // 500 milliseconds is half a second  

      } catch (Exception e) {  
        e.printStackTrace();
      }
      
    
    }
  }
}
