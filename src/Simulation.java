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
  public boolean runCycle (int algoID) {
    
    double currentTime = this.cycleNum*this.Svalue + this.Svalue;
    double endTime = currentTime + this.Svalue;
    
    ArrayList<Integer> whitelist = new ArrayList<Integer>();
    
    for (int i = 0; i < this.tracks.size(); i++) {
      whitelist.add(i);
    }

    ArrayList<Integer> temp = new ArrayList<Integer>(1);
    temp.add(this.currentPos);
    whitelist.removeAll(temp);

    this.generateMessages(currentTime);

    for (Message each: this.messageStack) {  // removeall
      if (each.getArrival() <= currentTime) {
        ArrayList<Integer> num = new ArrayList<Integer>(1);
        num.add(each.getOrigin());
        whitelist.removeAll(num); // remove the track from whitelist
      }
    }

    final double cT = currentTime;

    this.messageStack.removeIf(n -> (n.getArrival() <= cT));

    System.out.println("Current Cycle: " + this.cycleNum + " On Track: " + this.currentPos + " Health: " + this.mainHealth);

    int jumpTo = 0;

    if (algoID == 0) {
      jumpTo = this.algo0(currentTime, endTime);;
    } else if (algoID == 1) {
      jumpTo = this.algo1(currentTime, endTime);
    } else if (algoID == 2) {
      jumpTo = this.algo2(whitelist, currentTime, endTime);
    }

    
    if (jumpTo < 0) {
      return false;
    }
    

    return true;
  }

  public void resetSim () {
    this.cycleNum = 0;
    this.currentPos = 0;
    this.mainHealth = 200;
  }

  public int algo0 (double currentTime, double endTime) {
    ArrayList<Integer> tryTracks = new ArrayList<>(this.tracks.size());
    for (int i = 0; i < this.tracks.size(); i++) {
      if (i != this.currentPos) {
        tryTracks.add(i);
      }
    }

    for (int i : tryTracks) {
      if (this.persentDuring(this.tracks.get(i), currentTime)) {
        System.out.println(" - Collision! Track " + i + " was occupied.          At time: " + currentTime + " seconds. Health -5."); // hit occupied track
        
        this.mainHealth -= 5;

        if (this.mainHealth <= 0) {
          return -1;
        }
      } else {

        double arrives = arrivalDuring(this.tracks.get(i), currentTime, endTime);
      
        if (arrives < 0) { // -1 if you don't hit anything
        
          this.currentPos = i;
          this.cycleNum++;
          return i;

          // you got out !
        
        } else {

          System.out.println(" - Collision! Track " + i + " had an incoming train. At time: " + currentTime + " seconds. Health -20.");
          
          // hit incoming train !!
          this.mainHealth -= 20;

          if (this.mainHealth <= 0) {
            return -1;
          }

          currentTime = arrives;
          return algo0(currentTime, endTime);
        }

      }
    }

    return -1;
  }

  public int algo1 (double currentTime, double endTime) {
    
    ArrayList<Integer> tryTracks = new ArrayList<>(this.tracks.size());
    for (int i = 0; i < this.tracks.size(); i++) {
      if (i != this.currentPos) {
        tryTracks.add(i);
      }
    }
    Collections.shuffle(tryTracks);

    for (int i : tryTracks) {
      if (this.persentDuring(this.tracks.get(i), currentTime)) {
        System.out.println(" - Collision! Track " + i + " was occupied.          At time: " + currentTime + " seconds. Health -5."); // hit occupied track
        
        this.mainHealth -= 5;

        if (this.mainHealth <= 0) {
          return -1;
        }
      } else {

        double arrives = arrivalDuring(this.tracks.get(i), currentTime, endTime);
      
        if (arrives < 0) { // -1 if you don't hit anything
        
          this.currentPos = i;
          this.cycleNum++;
          return i;

          // you got out !
        
        } else {

          System.out.println(" - Collision! Track " + i + " had an incoming train. At time: " + currentTime + " seconds. Health -20.");
          
          // hit incoming train !!
          this.mainHealth -= 20;

          if (this.mainHealth <= 0) {
            return -1;
          }

          currentTime = arrives;
          return algo1(currentTime, endTime);
        }

      }
    }

    return -1;

  }

  public int algo2 (ArrayList<Integer> whitelist, double currentTime, double endTime) {

    int tryTrack = 0;
    Random random = new Random();

    while (true) {

      if (whitelist.size() > 0) {

        tryTrack = whitelist.remove(random.nextInt(whitelist.size()));

        if (persentDuring(this.tracks.get(tryTrack), currentTime)) {
        
          System.out.println(" - Collision! Track " + tryTrack + " was occupied.          At time: " + currentTime + " seconds. Health -5."); // hit occupied track
          
          this.mainHealth -= 5;
  
          if (this.mainHealth <= 0) {
            return -1;
          }

          // keep trying, next whitelist track

        } else {

          double arrives = arrivalDuring(this.tracks.get(tryTrack), currentTime, endTime);
        
          if (arrives < 0) { // -1 if you don't hit anything
          
            this.currentPos = tryTrack;
            this.cycleNum++;
            return tryTrack;

            // you got out !
          
          } else {

            System.out.println(" - Collision! Track " + tryTrack + " had an incoming train. At time: " + currentTime + " seconds. Health -20.");
            
            // hit incoming train !!
            this.mainHealth -= 20;

            if (this.mainHealth <= 0) {
              return -1;
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
          return -1;
        }

        // keep trying, next whitelist track

      } else {

        double arrives = arrivalDuring(this.tracks.get(tryTrack), currentTime, endTime);
      
        if (arrives < 0) { // -1 if you don't hit anything
        
          this.currentPos = tryTrack;
          this.cycleNum++;
          return tryTrack;

          // you got out !
        
        } else {

          System.out.println(" - Collision! Track " + tryTrack + " had an incoming train. At time: " + currentTime + " seconds. Health -20.");
          
          this.mainHealth -= 60;

          if (this.mainHealth <= 0) {
            return -1;
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

    return -1;
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

  public void generateMessages (double time) {  // false messages?

    Random random = new Random(); 
    for (Hobo each: this.hobos) {

      boolean hasTrain = this.persentDuring(this.tracks.get(each.getPos()), time);
      
      if (random.nextFloat() > 0.600 && hasTrain) {
        
        double delay = this.Svalue/2 - random.nextDouble();
        double sentAt = this.cycleNum*this.Svalue + random.nextDouble()*this.Svalue;
        
        
        // change truthiness of message
        this.messageStack.add(new Message(each.getPos(), delay, sentAt));
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

