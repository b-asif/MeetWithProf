package s25.cs151.application;

public class CourseInfo {
    private String courseCode;
    private String courseName;
    private String sectionNum;

    public CourseInfo(String code, String name, String num) {
        this.courseCode = code;
        this.courseName = name;
        this.sectionNum = num;
    }

    public String getCourseCode() {return courseCode;}
    public String getCourseName() {return courseName;}
    public String getSectionNum() {return sectionNum;}

}
