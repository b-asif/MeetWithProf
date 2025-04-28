package s25.cs151.application.model;

public class DataEntry {
    private String studentName;
    private String time;
    private String course;
    private String date;
    private String reason;
    private String comments;

    public DataEntry(String name, String time, String course, String date, String reason, String comments) {
        this.studentName = name;
        this.time = time;
        this.course = course;
        this.date = date;
        this.reason = reason;
        this.comments = comments;
    }

    public String getStudentName() {return studentName;}

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
