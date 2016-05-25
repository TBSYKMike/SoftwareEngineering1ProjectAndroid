package com.example.iuliu.androiddb;

/**
 * Created by Mike on 2016-05-18.
 */
public class Activity_SMS_TempValues {
    private static Activity_SMS_TempValues ourInstance = new Activity_SMS_TempValues();

    public static Activity_SMS_TempValues getInstance() {
        return ourInstance;
    }

    private Activity_SMS_TempValues() {
    }

    private String RandomNumber;
    private String PhoneNumber;
    private boolean validateNumberOK;
    private boolean validateRandomNumberOK;
    private boolean checkSMSOn = true;

    public String getRandomNumber() {
        return RandomNumber;
    }

    public void setRandomNumber(String randomNumber) {
        RandomNumber = randomNumber;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public void validateNumber(String phoneNumber){

        if(phoneNumber.contains(this.PhoneNumber.substring(1))) {
            validateNumberOK = true;
        }
        else {
            validateNumberOK = false;
        }
    }

    public void validateRandomNumber(String RandomNumber){
        if(RandomNumber.contains(this.RandomNumber)) {
            validateRandomNumberOK = true;
        }
        else {
            validateRandomNumberOK = false;
        }
    }
    public boolean validationCleared(){
        if (validateNumberOK && validateRandomNumberOK){
            resetValidattion();
            setCheckSMSOn(false);
            return true;
        } else{
            resetValidattion();
            return false;
        }
    }

    private void resetValidattion(){
        validateNumberOK = false;
        validateRandomNumberOK = false;
    }

    public boolean isCheckSMSOn() {
        return checkSMSOn;
    }

    public void setCheckSMSOn(boolean checkSMSOn) {
        this.checkSMSOn = checkSMSOn;
    }
}
