

public class Message {
  
  private int origin;
  private double arrival;
  
  
  public Message (int from, double delay, double sentAt) {
    this.arrival = sentAt + delay;
    this.origin = from;
  }

  public double getArrival () {
    return this.arrival;
  }

  public int getOrigin () {
    return this.origin;
  }

  public String toString () {
    return "From track: " + this.origin + " - Message arrival: " + this.arrival;
  }
  
  


}
