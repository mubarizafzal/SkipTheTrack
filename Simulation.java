import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Collections;

public class Simulation {

  // keeps track of the state of the simulation, simulation is played through Main.java
  
  private int cycleNum;
  
  private int mainHealth = 200;

  private double Svalue;
  
  private ArrayList<Track> tracks; // Track objects instead of boolean
  
  private ArrayList<Hobo> hobos;

  private ArrayList<Message> messageStack;

  public Simulation (double Svalue, int numTracks, int numTrains, double avgDuration, double avgDelay, int numHobos) {
  
    this.tracks = new ArrayList<Track>(numTracks);
    
    for (int i = 0; i < numTracks; i++) {
      this.tracks.add(new Track(i, avgDuration, avgDelay, numTrains));
    }

    this.hobos = new ArrayList<Hobo>(numHobos);

    for (int i = 0; i < numHobos; i++) {
      this.hobos.add(new Hobo(i));
    }
    
    this.messageStack = new ArrayList<Message>();
    this.cycleNum = 0;
    this.Svalue = Svalue;
    
    
  }
  
  // this function will make the calculations, move the simulation one cycle forward
  public void runCycle () {
    double currentTime = this.cycleNum*this.Svalue + this.Svalue;
    Set<Integer> blacklist = new HashSet<>();

    this.generateMessages();
    for (Message each: messageStack) {
      if (each.getArrival() <= currentTime) {
        blacklist.add(each.getOrigin());
        messageStack.remove(each);
      }
    }
    
    
  
  }

  public ArrayList<Message> getMessages () {
    return this.messageStack;
  }

  public void generateMessages () {
    // if chance to send message
    //
    Random random = new Random(); 
    for (int i = 0; i < this.hobos.size(); i++) {
      
      if (random.nextFloat() > 0.600) {
        
        double delay = this.Svalue/2 - random.nextDouble();
        double sentAt = this.cycleNum*this.Svalue + random.nextDouble()*this.Svalue;
        
        
        // change truthiness of message
        this.messageStack.add(new Message(this.hobos.get(i).getPos(), delay, sentAt, true));
      }
    }
    Collections.sort(messageStack, new MessageComparator());

  }

  // sets the safety value for each track for the given time
  public void setSafety (double currentTime) {

    
  }



}

class MessageComparator implements Comparator<Message> {

  public int compare (Message a, Message b) {
    if (a.getArrival() > b.getArrival()) return 1;
    if (a.getArrival() < b.getArrival()) return -1;
    return 0;
  }

}

