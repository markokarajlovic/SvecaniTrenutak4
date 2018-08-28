package com.ephoenixdev.svecanitrenutak.models;

public class UserModel {

        private String uid;
        private String phoneNumber;
        private boolean isAdmin;
        private String profileImageName;

        public UserModel(){

        }

    public UserModel(String uid, String phoneNumber, boolean isAdmin, String profileImageName) {
        this.uid = uid;
        this.phoneNumber = phoneNumber;
        this.isAdmin = isAdmin;
        this.profileImageName = profileImageName;
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

    public String getProfileImageName() {
        return profileImageName;
    }

    public void setProfileImageName(String profileImageName) {
        this.profileImageName = profileImageName;
    }
}
