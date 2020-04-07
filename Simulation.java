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

  public Simulation (double Svalue, int numTracks, int numTrains, double avgOccupation, double avgDelay, int numHobos) {
  
    this.tracks = new ArrayList<Track>(numTracks);
    
    for (int i = 0; i < numTracks; i++) {
      this.tracks.add(new Track(i, avgOccupation, avgDelay, numTrains));
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
    
    for (int i = 0; i < this.tracks.size(); i++) {
      whitelist.add(i);
    }

    ArrayList<Integer> temp = new ArrayList<Integer>(1);
    temp.add(this.currentPos);
    whitelist.removeAll(temp);

    this.generateMessages();

    for (Message each: this.messageStack) {  // removeall
      if (each.getArrival() <= currentTime) {
        ArrayList<Integer> num = new ArrayList<Integer>(1);
        num.add(each.getOrigin());
        whitelist.removeAll(num); // remove the track from whitelist
      }
    }

    final double cT = currentTime;

    this.messageStack.removeIf(n -> (n.getArrival() <= cT));


    int tryTrack = 0; // for testing

    System.out.println("Current Cycle: " + this.cycleNum + " On Track: " + this.currentPos + " Health: " + this.mainHealth);

    while (true) {

      if (whitelist.size() > 0) {

        tryTrack = whitelist.remove(random.nextInt(whitelist.size()));

        if (persentDuring(this.tracks.get(tryTrack), currentTime)) {
        
          System.out.println(" - Collision! Track " + tryTrack + " was occupied.          At time: " + currentTime + " seconds. Health -5."); // hit occupied track
          
          this.mainHealth -= 5;
  
          if (this.mainHealth <= 0) {
            return false;
          }

          // keep trying, next whitelist track

        } else {

          double arrives = arrivalDuring(this.tracks.get(tryTrack), currentTime, endTime);
        
          if (arrives < 0) { // -1 if you don't hit anything
          
            this.currentPos = tryTrack;
            this.cycleNum++;
            return true;

            // you got out !
          
          } else {

            System.out.println(" - Collision! Track " + tryTrack + " had an incoming train. At time: " + currentTime + " seconds. Health -20.");
            
            // hit incoming train !!
            this.mainHealth -= 20;

            if (this.mainHealth <= 0) {
              return false;
            }

            currentTime = arrives;
          }
        }  
      } else {
        break;
      }
    }

    // only get here after whitelist is exhausted
    tryTrack = 0;

    while (true) {

      // dont try the track we're on
      if (tryTrack == this.currentPos) {
        tryTrack++;
      }

      if (persentDuring(this.tracks.get(tryTrack), currentTime)) {
        
        System.out.println(" - Collision! Track " + tryTrack + " was occupied.          At time: " + currentTime + " seconds. Health -5."); // hit occupied track
        
        this.mainHealth -= 40;

        if (this.mainHealth <= 0) {
          return false;
        }

        // keep trying, next whitelist track

      } else {

        double arrives = arrivalDuring(this.tracks.get(tryTrack), currentTime, endTime);
      
        if (arrives < 0) { // -1 if you don't hit anything
        
          this.currentPos = tryTrack;
          this.cycleNum++;
          return true;

          // you got out !
        
        } else {

          System.out.println(" - Collision! Track " + tryTrack + " had an incoming train. At time: " + currentTime + " seconds. Health -20.");
          
          this.mainHealth -= 60;

          if (this.mainHealth <= 0) {
            return false;
          }

          currentTime = arrives;
          tryTrack = 0;
        }
      }
      tryTrack++;

      if (tryTrack == this.tracks.size()) {
        break;
      }

    }

    System.out.println("No where to go!");
    return false;
  
  }

  public void shuffleHobos () {
    Random random = new Random();
    for (Hobo each: this.hobos) {
      each.setPos(random.nextInt(hobos.size()));
    }
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

  public void generateMessages () {  // false messages?

    Random random = new Random(); 
    for (int i = 0; i < this.hobos.size(); i++) {
      
      if (random.nextFloat() > 0.600) {
        
        double delay = this.Svalue/2 - random.nextDouble();
        double sentAt = this.cycleNum*this.Svalue + random.nextDouble()*this.Svalue;
        
        
        // change truthiness of message
        this.messageStack.add(new Message(this.hobos.get(i).getPos(), delay, sentAt));
      }
    }
    Collections.sort(messageStack, new MessageComparator());
  }

}

class MessageComparator implements Comparator<Message> {

  public int compare (Message a, Message b) {
    if (a.getArrival() > b.getArrival()) return 1;
    if (a.getArrival() < b.getArrival()) return -1;
    return 0;
  }

}

