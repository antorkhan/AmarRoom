package com.example.akib.amarroomapp.models;

public class Posts {
    public int p_id,u_id,rent;
    public String image,address,note;

    public Posts(int p_id, int u_id, int rent, String image, String address, String note) {
        this.p_id = p_id;
        this.u_id = u_id;
        this.rent = rent;
        this.image = image;
        this.address = address;
        this.note = note;
    }

    public int getP_id() {
        return p_id;
    }

    public int getU_id() {
        return u_id;
    }

    public int getRent() {
        return rent;
    }

    public String getImage() {
        return image;
    }

    public String getAddress() {
        return address;
    }

    public String getNote() {
        return note;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
