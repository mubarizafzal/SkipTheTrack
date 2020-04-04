

public class Message {
  
  private int origin;
  private double arrival;
  private boolean isSafe = false;
  
  
  public Message (int from, double delay, double sentAt, boolean safety) {
    this.arrival = sentAt + delay;
    this.origin = from;
    this.isSafe = safety;
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
