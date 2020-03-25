/**
 * This program is a simulator that shows how long the main hobo can survive jumping from track to track
 * We also can make it into what we want it to be, because the prof left it vague in a lot of places
 * 
 * 
 * The main hobo interacts with the following elements of the environment (in its attempt to survive):
 * 	- Trains
 * 		- determine timings for each track (negatively exponential formula or similar)
 * 	- Other hobos
 * 		- message failure mechanism
 * 		- delay in message after sending
 * 		- chance of message being misread
 * 	-  Variable S, fixed value of how long the main hobo stays on one track before moving
 * 	- health and damage system
 * 
 * The user sets the following variables at the beginning of the simulation:
 * 	- variable S
 * 	- number of tracks
 * 	- number of other hobos
 * 	- the distribution and average duration a train move through the tunnel
 * 	- the distribution and average delay before next train
 * 	- the number of other hobos within the environment
 * ------------------------------------------------------
 * Above are all things mentioned by the prof
 * 
 * New details:
 * 
 * Need to make different algorithms that help the main hobo decide where to jump:
 * 	- benchmark algorithm: move one track up or down, as soon as your time is up or you are hit
 * 	- algo1: randomly jump to a select track
 * 	- algo2: use all hobo info to help you decide where to jump
 * 	- algo3: use later half of hobo info to help you decide where to jump
 *
 * We can say that every S seconds, all the hobos jump, the main hobo jumps according to its algorithm, while the other jump to rand tracks
 * We can also say that the other hobos dont die, and they can stay on occupied tracks to make it simple
 * 
 * We can imitate the 'other hobos' by designing it such that whenever a track is occupied by a train, there is a random chance during
 * that time that a 'message' will be sent to the main hobo, with a random delay, that 'this track is occupied'
 * 
 * msg array
 * 
 * 
 */

// every S seconds generate new message


// send stack, receive stack, delay generator

import java.util.*;

class Main {

  public static void main (String args[]) {
  
    System.out.println("hellooo");
    
    
    Simulation mySim = new Simulation(12.5, 10, 25.5, 28.5, 6);
    
    
  
  
  }



}



