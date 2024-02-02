package dev.m3s.programming2.homework2;
import java.util.Scanner;

public class PersonID {
    private String birthDate = ConstantValues.NO_BIRTHDATE;

    public String getBirthDate() {
        return birthDate;
    }

    public String setPersonID(final String personID){
        if (!checkPersonIDNumber(personID)){
            return ConstantValues.INVALID_BIRTHDAY;
        }
        else {

            String birthdate = "";
            birthdate += personID.substring(0,2);
            birthdate += ".";
            birthdate += personID.substring(2,4);
            birthdate += ".";

            switch(personID.charAt(6)){
                case '+':
                    birthdate += "18";
                    break;

                case '-':
                    birthdate += "19";
                    break;

                case 'A':
                    birthdate += "20";
                    break;
            }

            birthdate += personID.substring(4,6);

            if (!checkBirthdate(birthdate)){
                return ConstantValues.INVALID_BIRTHDAY;
            } else if (!checkValidCharacter(personID)){
                return ConstantValues.INCORRECT_CHECKMARK;
            }
            else{
                this.birthDate = birthdate;
                return "Ok";
            }
        }
    }

    private boolean checkPersonIDNumber(final String theID){
        if(theID == null){
            return false;
        }
        if (theID.length() != 11 ||
                (theID.charAt(6) != 'A' &&
                        theID.charAt(6) != '+' &&
                        theID.charAt(6) != '-'))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    private boolean checkLeapYear(int year){

        boolean lpYr = true;

        if (year % 4 != 0) {
            lpYr = false;
        }
        else {
            if ((year % 100 == 0) && (year % 400 != 0))
                lpYr = false;
        }

        return lpYr;
    }
    private boolean checkValidCharacter(final String personId){

        String checkString = "0123456789ABCDEFHJKLMNPRSTUVWXY";
        String numStr = personId.substring(0,6);
        numStr += personId.substring(7,10);
        int number = Integer.parseInt(numStr);
        int res = number % 31;
        return (checkString.charAt(res) == personId.charAt(10));
    }
    private boolean checkBirthdate(final String date){
        boolean retVal = true;
        Scanner scanner = new Scanner(date);
        Scanner s = scanner.useDelimiter("\\.");
        int day = s.nextInt();
        int month = s.nextInt();
        int year = s.nextInt();
        s.close();

        if (year < 0 || month < 1 || month > 12 || day < 1 || day > 31) {
            retVal = false;
        }
        else {
            switch(month){
                case 4:
                case 6:
                case 9:
                case 11:
                    if (day == 31)
                        retVal = false;
                    break;
                case 2:
                    if (day > 29)
                        retVal = false;
                    if (day == 29)
                        retVal = checkLeapYear(year);
                    break;
            }
        }
        scanner.close();
        return retVal;
    }


}
