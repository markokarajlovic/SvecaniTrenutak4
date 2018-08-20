package com.ephoenixdev.svecanitrenutak.models;

public class UserModel {

        private String uid;
        private String phoneNumber;
        private boolean isAdmin;

        public UserModel(){

        }

    public UserModel(String uid, String phoneNumber, boolean isAdmin) {
        this.uid = uid;
        this.phoneNumber = phoneNumber;
        this.isAdmin = isAdmin;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
