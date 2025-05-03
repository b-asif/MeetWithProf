package s25.cs151.application.model;

public class DataEntry {
    private String studentName;
    private String time;
    private String course;
    private String date;
    private String reason;
    private String comments;
    private int id;

    public DataEntry(String name, String time, String course, String date, String reason, String comments, int id) {
        this.studentName = name;
        this.time = time;
        this.course = course;
        this.date = date;
        this.reason = reason;
        this.comments = comments;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
