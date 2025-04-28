package s25.cs151.application.model;

public class TimeSlot {
    private String startTime;
    private String endTime;

    public TimeSlot(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getStartTime() {return startTime;}
    public String getEndTime() {return endTime;}

    //setting the start and end times
    public void setStartTime() {this.startTime = startTime;}
    public void setEndTime() {this.endTime = endTime;}
}


