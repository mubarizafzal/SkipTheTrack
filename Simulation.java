import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Random;
import java.util.Comparator;

public class Simulation {

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

  private PriorityQueue<Message> messageStack;

  public Simulation (double Svalue, int numTracks, int numTrains, double avgDuration, double avgDelay, int numHobos) {
  
    
    for (int i = 0; i < numTracks; i++) {
      this.tracks.add(new Track(i, avgDuration, avgDelay, numTrains));
    }

    for (int i = 0; i < numHobos; i++) {
      this.hobos.add(new Hobo(i));
    }
    
    this.messageStack = new PriorityQueue<Message>(numHobos*2, new MessageComparator());
    this.cycleNum = 0;
    this.Svalue = Svalue;
    
    
  }
  
  // this function will make the calculations, move the simulation one cycle forward
  public void runCycle () {
    
    
  
  }

  public void generateMessages () {
    // if chance to send message
    //
    Random random = new Random(); 
    for (int i = 0; i < hobos.size(); i++) {
      if (random.nextFloat() > 0.600) {
        double delay = this.Svalue/2 - random.nextDouble();
        double sentAt = this.cycleNum*this.Svalue + random.nextDouble()*this.Svalue;
        messageStack.add(new Message(hobos.get(i).getPos(), delay, sentAt));
      }
    }
  }

  // sets the safety value for each track for the given time
  public void setSafety (double currentTime) {

    
  }



}

class MessageComparator implements Comparator<Message> {

  public int compare (Message a, Message b) {
    return -1;
  }

}

