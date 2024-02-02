package dev.m3s.programming2.homework2;
public class Degree {
    private static final int MAX_COURSES = 50;
    private int count;
    private String degreeTitle = ConstantValues.NO_TITLE;
    private String titleOfThesis = ConstantValues.NO_TITLE;
    private StudentCourse[] myCourses = new StudentCourse[MAX_COURSES];

    public Degree(){
        
    }

    public StudentCourse[] getCourses() {
        return myCourses;
    }

    public void addStudentCourses(StudentCourse[] courses) {
        if (courses != null && courses.length <= MAX_COURSES) {
            for (StudentCourse course : courses) {
                addStudentCourse(course);
            }
        }
    }

    public boolean addStudentCourse(StudentCourse course) {
        if (course != null && count < MAX_COURSES) {
            myCourses[count] = course;
            count++;
            return true;
        }
        return false;
    }

    public String getDegreeTitle() {
        return degreeTitle;
    }

    public void setDegreeTitle(String degreeTitle) {
        if (degreeTitle != null) {
            this.degreeTitle = degreeTitle;
        }
    }

    public String getTitleOfThesis() {
        return titleOfThesis;
    }

    public void setTitleOfThesis(String titleOfThesis) {
        if (titleOfThesis != null) {
            this.titleOfThesis = titleOfThesis;
        }
    }

    public double getCreditsByBase(char base) {
        double credits = 0;
        for (StudentCourse course : myCourses) {
            if (course != null && course.getBase() == base && isCourseCompleted(course)) {
                credits += course.getCredits();
            }
        }
        return credits;
    }

    public double getCreditsByType(int courseType) {
        double credits = 0;
        for (StudentCourse course : myCourses) {
            if (course != null && course.getCourseType() == courseType && isCourseCompleted(course)) {
                credits += course.getCredits();
            }
        }
        return credits;
    }

    public double getCredits() {
        double credits = 0;
        for (StudentCourse course : myCourses) {
            if (course != null && isCourseCompleted(course)) {
                credits += course.getCredits();
            }
        }
        return credits;
    }

    private boolean isCourseCompleted(StudentCourse c) {
        if(c == null){
            return false;
        }
        return c.isPassed();
    }

    public void printCourses() {
        for (StudentCourse course : myCourses) {
            if (course != null) {
                System.out.println(course.toString());
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Degree [Title: \"").append(getDegreeTitle()).append("\" (courses: ").append(count).append(")\n");
        if (getTitleOfThesis() != null) {
            sb.append("Thesis title: \"").append(getTitleOfThesis()).append("\"\n");
        }
        int i = 1;
        for (StudentCourse course : myCourses) {
            if (course != null) {
                sb.append(i++).append(". ").append(course.toString()).append("\n");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
