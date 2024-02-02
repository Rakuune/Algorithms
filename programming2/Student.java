package dev.m3s.programming2.homework2;
import java.util.Random;

public class Student {
    private String firstName = ConstantValues.NO_NAME;
    private String lastName = ConstantValues.NO_NAME;
    private int id;
    private int startYear = ConstantValues.CURRENT_YEAR;
    private int graduationYear;
    private Degree degree = new Degree();
    private String birthDate = ConstantValues.NO_BIRTHDATE;
    private Random ran = new Random();
    
    public Student() {
        this.id = getRandomId();
        this.startYear = ConstantValues.CURRENT_YEAR;
    }

    public Student(String lname, String fname) {
        this();
        if(lname != null){
            this.lastName = lname;
        }
        if(fname != null){
            this.firstName = fname;
        }
    }

    private int getRandomId(){
        int randomId = ConstantValues.MIN_ID + ran.nextInt(ConstantValues.MAX_ID);
        return randomId;
    }



    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName != null) {
            this.firstName = firstName;
        }
    }


    public String getLastName() {
        return this.lastName;
    }


    public void setLastName(String lastName) {
        if (lastName != null) {
            this.lastName = lastName;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id >= ConstantValues.MIN_ID && id <= ConstantValues.MAX_ID) {
            this.id = id;
        }
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        if (startYear > 2000 && startYear <= ConstantValues.CURRENT_YEAR) {
            this.startYear = startYear;
        }
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public String setGraduationYear(int graduationYear) {
        if (canGraduate() && graduationYear >= startYear && graduationYear <= ConstantValues.CURRENT_YEAR) {
            this.graduationYear = graduationYear;
            return "Ok";
        } else if (!hasCompletedRequiredCredits()) {
            return "Check amount of required credits";
        } else {
            return "Check graduation year";
        }
    }

    public void setDegreeTitle(String dName) {
        if (dName != null && this.degree != null) {
            degree.setDegreeTitle(dName);
        }
    }

    public boolean addCourse(StudentCourse course) {
        return degree != null && degree.addStudentCourse(course);
    }

    public int addCourses(StudentCourse[] courses) {
        int numAdded = 0;
        if (courses != null) {
            for (StudentCourse course : courses) {
                if (course != null && addCourse(course)) {
                    numAdded++;
                }
            }
        }
        return numAdded;
    }

    public void printDegree() {
        if (degree != null) {
            System.out.println(degree.toString());
        }
    }

    public void printCourses(){
        if(this.degree != null){
            for (StudentCourse course : degree.getCourses()){
                if(course != null){
                    System.out.println(course.getCourse().toString() + " " + course.toString());
                }
            }
        }        
    }

    public void setTitleOfThesis(String title){
        if(title != null){
            degree.setTitleOfThesis(title);
        }
    }

    public boolean hasGraduated() {
        if (graduationYear != 0){
            return true;
        } else {
            return false;
        }
    }

    public int getStudyYears(){
        if(canGraduate() && hasGraduated()){
            return (this.graduationYear - this.startYear);
        } else{
            return (ConstantValues.CURRENT_YEAR - this.startYear);
        }
    }

    public String getBirthDate(){
        return this.birthDate;
    }

    public String setBirthDate(String personId) {
        if (personId != null) {
            PersonID checkBday = new PersonID();
            String result = checkBday.setPersonID(personId);
            if(result.equals("Ok")){
                this.birthDate = checkBday.getBirthDate();
                return this.birthDate;
            } else{
                return "No change";
            }
        }
        return "No change";
    }

    public boolean hasCompletedRequiredCredits() {
        int totalCredits = 0;
        for (StudentCourse course : degree.getCourses()) {
            if (course != null && degree.getTitleOfThesis() != ConstantValues.NO_TITLE && degree.getTitleOfThesis() != null){
                totalCredits += course.getCredits();
            }
        }
        return totalCredits >= 180;
    }


    private boolean canGraduate() {
        if (hasCompletedRequiredCredits() && degree.getTitleOfThesis() != ConstantValues.NO_TITLE && this.degree != null){
            return true;
        } else{
            return false;
        }
    }

    public String checkStatus(){
        if(hasGraduated()){
            return "The student has graduated in " + getGraduationYear();
        } else {
            return "The student has not graduated, yet.";
        }
    }

    public String toString(){
        String studentId = "Student id: "+ getId();
        String studentFirstName = "\n" + "\t" + "FirstName: " + getFirstName();
        String studentLastName = "LastName: " + getLastName();
        String studentBirthDate = "\n" + "\t" + "Date of Birth: " + getBirthDate();
        String studentStatus = "\n" + "\t" + "Status: " + checkStatus();
        String startYearStr = "\n" + "\t" + "StartYear: " + getStartYear() + "(studies have lasted for " + getStudyYears() + " years)";
        String credits = "\n" + "\t" + "Credits: " + degree.getCredits();
        String totalCredits = "\n" + "\t" + "\t" + "Total credits completed (" + degree.getCredits() + "/180.0)";
        String titleOfThesis = "\n" + "\t" + "\t" + "Title of Thesis: " + "\"" + degree.getTitleOfThesis() + "\"";
        String toStringList = studentId + studentFirstName + ", " + studentLastName + studentBirthDate + studentStatus + startYearStr + credits
                + totalCredits + titleOfThesis;

        return toStringList;
    }

}
