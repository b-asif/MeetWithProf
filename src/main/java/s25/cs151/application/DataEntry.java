package s25.cs151.application;

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

    public String getTime() {
        return time;
    }

    public String getCourse() {
        return course;
    }

    public String getDate() {
        return date;
    }

    public String getComments() {
        return comments;
    }

    public String getReason() {
        return reason;
    }
}
