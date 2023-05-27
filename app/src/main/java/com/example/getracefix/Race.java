package com.example.getracefix;




public class Race {
    private String Origin;
    private String Destination;
    private String Email;
    private String phoneNumber;
    private String carModel;
    private String plateNumber;



       public Race(){

       }

       public Race (String Origin , String Destination , String Email , String phoneNumber , String carModel , String plateNumber){
        //get and set
           this.Origin = Origin;
           this.Destination = Destination;
           this.Email = Email;
           this.phoneNumber = phoneNumber;
           this.carModel = carModel;
           this.plateNumber = plateNumber;

       }
    //constructor
    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }
}
