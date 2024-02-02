package dev.m3s.programming2.homework2;
public class Course {
    private String name;
    private String courseCode;
    private Character courseBase;
    private int courseType;
    private int period;
    private double credits;
    private boolean numericGrade;

    public Course() {
    }

    public Course(String name, final int code, Character courseBase, final int courseType, final int period,
                  double credits, boolean numericGrade) {
        setName(name);
        setCourseCode(code, courseBase);
        setCourseType(courseType);
        setPeriod(period);
        setCredits(credits);
        setNumericGrade(numericGrade);
    }

    public Course(Course course){
        this.name = course.name;
        this.courseCode = course.courseCode;
        this.courseBase = course.courseBase;
        this.courseType = course.courseType;
        this.period = course.period;
        this.credits = course.credits;
        this.numericGrade = course.numericGrade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name != null && name != "") {
            this.name = name;
        }
    }

    public String getCourseTypeString(){
        if (getCourseType() == 0){
            return "Optional";
        }
        else{
            return "Mandatory";
        }
    }

    public int getCourseType() {
        return courseType;
    }

    public void setCourseType(int courseType) {
        if(courseType == ConstantValues.OPTIONAL || courseType == ConstantValues.MANDATORY){
            this.courseType = courseType;
        }
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(final int courseCode, Character courseBase) {
    if (0 < courseCode && courseCode < 1000000) {
        if (courseBase != null) {
            if (courseBase == 'a' || courseBase == 'p' || courseBase == 's') {
                courseBase = Character.toUpperCase(courseBase);
            }
            if (courseBase == 'A' || courseBase == 'P' || courseBase == 'S') {
                String strCode = "" + courseCode;
                this.courseCode = strCode + courseBase;
                this.courseBase = courseBase;
            }
        }
    }
}

    public Character getCourseBase() {
        return courseBase;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        if(ConstantValues.MIN_PERIOD <= period && period <= ConstantValues.MAX_PERIOD){
            this.period = period;
        }
    }

    public double getCredits() {
        return credits;
    }

    private void setCredits(double credits) {
        if(ConstantValues.MIN_CREDITS <= credits && credits <= ConstantValues.MAX_COURSE_CREDITS){
            this.credits = credits;
        }
    }

    public boolean isNumericGrade(){
        if (this.numericGrade ==  true){
            return true;
        } else{
            return false;
        }
    }

    public void setNumericGrade(boolean numericGrade) {
        this.numericGrade = numericGrade;
    }

    public String toString(){
        String dbQt = "\"";
        String credits = String.format("%.2f", getCredits());
        String courseInfo = "[" + getCourseCode() + " (" + credits + " cr), " + dbQt + getName() + dbQt
                + ". " + getCourseTypeString() + ", " + "period: " + getPeriod() + ".]";
        return  courseInfo;
    }

    public char getBase() {
        return this.courseBase;
    }

    public int getType() {
        return this.courseType;
    }
}
