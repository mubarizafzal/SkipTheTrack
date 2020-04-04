

public class Message {
  
  private int fromTrack;
  private double delay;
  private double sentAt;
  private boolean isSafe = false;
  
  
  public Message (int from, double delay, double sentAt) {
    this.sentAt = sentAt;
    this.delay = delay;
    this.fromTrack = from;
  }
  
  


}
