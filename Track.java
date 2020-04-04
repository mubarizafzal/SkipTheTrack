import java.util.Collections;
import java.util.ArrayList;
import java.util.Random;

class Track {

  private int trackNum;
  private ArrayList<ArrayList<Double>> schedule = new ArrayList<>();

  public Track (int trackNum, double occupyAvg , double delayAvg, int numTrains) {

    this.schedule = makeSchedule(makeDistribution(occupyAvg, occupyAvg*2, numTrains), makeDistribution(delayAvg, delayAvg*2, numTrains), numTrains);

    this.trackNum = trackNum;


  }

  public ArrayList<ArrayList<Double>> getSchedule () {
    return this.schedule;
  }

  private ArrayList<ArrayList<Double>> makeSchedule (ArrayList<Double> occupiedTimes, ArrayList<Double> delayTimes, int size) {

    ArrayList<ArrayList<Double>> schedule = new ArrayList<>(size);
    double arrival = 0;
    for (int i = 0; i < size; i++) {
      arrival = arrival + delayTimes.get(i);
      
      ArrayList<Double> duration = new ArrayList<Double>(2);
      duration.add(arrival);
      duration.add(arrival + occupiedTimes.get(i));

      schedule.add(duration);
      arrival = arrival + occupiedTimes.get(i);
    }

    return schedule;

  }

  public ArrayList<Double> makeDistribution (double mean, double deviation, int size) {

    ArrayList<Double> distribution = new ArrayList<Double>(size);

    Random random = new Random();
    for (int i = 0; i < size; i++) {
      distribution.add((random.nextDouble()*deviation) + (mean - deviation/2));
    }
    Collections.sort(distribution);
    Collections.reverse(distribution);
    return distribution;
  }


}
