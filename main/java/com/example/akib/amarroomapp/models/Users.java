package com.example.akib.amarroomapp.models;

public class Users {
    public int u_id,rent;
    public String fullName,phoneNumber,password,address;

    public Users(int u_id, int rent, String fullName, String phoneNumber, String password, String address) {
        this.u_id = u_id;
        this.rent = rent;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.address = address;
    }


    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
