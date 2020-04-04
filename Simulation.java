import java.util.ArrayList;
import java.util.Random;
import java.util.Comparator;
import java.util.Collections;

public class Simulation {

  // keeps track of the state of the simulation, simulation is played through Main.java
  
  private int cycleNum;
  
  private int mainHealth;

  private int currentPos;

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
    this.currentPos = 0;
    this.mainHealth = 200;
    
    
  }
  
  // this function will make the calculations, move the simulation one cycle forward
  public boolean runCycle () {
    
    double currentTime = this.cycleNum*this.Svalue + this.Svalue;
    double endTime = currentTime + this.Svalue;
    
    ArrayList<Integer> whitelist = new ArrayList<Integer>();
    Random random = new Random();
    int jumpTo = random.nextInt(this.tracks.size());

    for (int i = 0; i < this.tracks.size(); i++) {
      whitelist.add(i);
    }

    this.generateMessages();
    
    for (Message each: messageStack) {
      if (each.getArrival() <= currentTime) {
        whitelist.remove(each.getOrigin()); // remove the track from whitelist
        messageStack.remove(each);
      }
    }

    int tryTrack = 0;

    while (this.tracks.size() - tryTrack != 0) {

      // go to a track from the whitelist if possible, else try tracks in sequence
      if (whitelist.size() > 0) {
        jumpTo = whitelist.remove(random.nextInt(whitelist.size()));
      } else {
        
        jumpTo = tryTrack;
        tryTrack++;
      }

      // present during

      if (persentDuring(this.tracks.get(jumpTo), currentTime)) {
        
        // hit occupied track
        
        continue;
      
      } else {

        double arrives = arrivalDuring(this.tracks.get(jumpTo), currentTime, endTime);
      
        if (arrives < 0) { // -1 if you don't hit anything
        
          break;
        
        } else {
  
          // hit incoming train !!
          
          tryTrack = 0;
          currentTime = arrives; 
        }
      }
    }

    // if don't find track
    if (tryTrack == tracks.size()) {
      return false;
    }

    this.currentPos = jumpTo;
    return true;

    // exit if health is gone
    // logging movement
    // shuffle hobos
    // what is the track at the end of the cycle?
    // damage, rerun cycle if hit by train before S is over


    
    
  
  }

  public boolean persentDuring (Track track, double time) {
    for (ArrayList<Double> inner: track.getSchedule()) {
      if (inner.get(0) > time) {
        return false;
      } else if (inner.get(1) > time) {
        return true;
      }

    }
    return false;
  }

  public double arrivalDuring (Track track, double cycleBegin, double cycleEnd) {
    for (ArrayList<Double> inner: track.getSchedule()) {
      if (inner.get(0) > cycleEnd) {
        return -1;
      } else if (inner.get(0) > cycleBegin) {
        return inner.get(0);        
      }
    }
    return -1;
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

  // sets the safety value for each track for the given time - remove?
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

