package com.example.getracefix;

public class ProfileNote {
    private String mCarModel;
    private String mPlateNumber;
    private String mPhoneNumber;


    public ProfileNote(){

    }
     //Get and set
    public ProfileNote(String carModel , String plateNumber , String phoneNumber){
        this.mCarModel = carModel;
        this.mPlateNumber = plateNumber;
        this.mPhoneNumber = phoneNumber;
    }
    //Constructor
    public String getmCarModel() {
        return mCarModel;
    }

    public void setmCarModel(String mCarModel) {
        this.mCarModel = mCarModel;
    }

    public String getmPlateNumber() {
        return mPlateNumber;
    }

    public void setmPlateNumber(String mPlateNumber) {
        this.mPlateNumber = mPlateNumber;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }
}
