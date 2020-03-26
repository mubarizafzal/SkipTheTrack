

class Track {
  
  private int trackNum;
  // schedule = new Arraylist...
  
  public Track (int trackNum, double average, double delay) {
  
    this.trackNum = trackNum;
    
    // use averge and delay to calculate a schedule that looks like:
    // you can use an arraylist that contains arraylists of doubles/floats
    // [[4.5,14.4], [18.1,29.2], ...]
    // where each element is [begin occupation time, end occupation time]
    
    
    // (0 to 4.5) is the first delay
    // (4.5 to 14.4) is first track occupation duration
    // (14.4 to 18.1) is the second delay
    // (18.1 to 29.2) is the second track occupation duration
  }
  
  


}
