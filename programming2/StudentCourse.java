import java.time.LocalDate;

public class StudentCourse {
    private Course course;
    private int gradeNum;
    private int yearCompleted;

    public StudentCourse() {
    }

    public StudentCourse(Course course, int gradeNum, int yearCompleted) {
        setCourse(course);
        setGrade(gradeNum);
        setYear(yearCompleted);
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getGradeNum() {
        return gradeNum;
    }

    protected void setGrade(int gradeNum) {
        if (checkGradeValidity(gradeNum)) {
            this.gradeNum = gradeNum;
            if (yearCompleted == 0) {
                setYear(LocalDate.now().getYear());
            }
        } else {
            System.out.println("Invalid grade value: " + gradeNum);
        }
    }

    private boolean checkGradeValidity(int gradeNum) {
        if (course.isNumericGrade()) {
            return gradeNum >= 0 && gradeNum <= 5;
        } else {
            char gradeChar = (char) gradeNum;
            gradeChar = Character.toUpperCase(gradeChar);
            return gradeChar == 'A' || gradeChar == 'F';
        }
}

    public boolean isPassed() {
        if (gradeNum == ConstantValues.GRADE_FAILED || gradeNum == 'F' || gradeNum == 'f' || gradeNum == 0) {
            return false;
        }
        return true;
}

    public int getYear() {
        return this.yearCompleted;
    }

    public void setYear(int year) {
        if (year > 2000 && year <= ConstantValues.CURRENT_YEAR) {
            this.yearCompleted = year;
        }
    }

    public String checkGradeType(){
        String gradeStr;
        if (getGradeNum() == 0) {
            gradeStr = "Not graded";
        } else if (getGradeNum() == 'F' || getGradeNum() == 'A') {
            gradeStr = "" + (char)getGradeNum();
        } else {
            gradeStr = "" + getGradeNum();
        }
        return gradeStr;
    }

    public String toString() {
        String courseInfo = course.toString() + " Year: " + getYear() + ", Grade: " + checkGradeType() + ".]";
        return courseInfo;
    }

    public double getCredits() {
        if (isPassed()) {
            return course.getCredits();
        } else {
            return 0.0;
        }
    }

    public char getBase() {
        return course.getBase();
    }

    public int getCourseType() {
        return course.getCourseType();
    }
}
