package com.ephoenixdev.svecanitrenutak.models;

import java.util.Date;

public class AdModel {

    private String adId;
    private String uid;
    private String title;
    private String category;
    private String description;
    private String cityOfAd;
    private String address;
    private String youTubeURL;
    private String fbURL;
    private String instagramURL;
    private String personalWebSite;
    private Date dateAndTimeOfEntry;
    private Date dateAndTimeOfExpire;
    private String imageOfTheAd;

    public AdModel() {
    }

    // bez datuma
    public AdModel(String adId,
                   String uid,
                   String title,
                   String imageOfTheAd,
                   String category,
                   String description,
                   String cityOfAd,
                   String address,
                   String youTubeURL,
                   String fbURL,
                   String instagramURL,
                   String personalWebSite) {
        this.adId = adId;
        this.uid = uid;
        this.title = title;
        this.category = category;
        this.description = description;
        this.cityOfAd = cityOfAd;
        this.address = address;
        this.youTubeURL = youTubeURL;
        this.fbURL = fbURL;
        this.instagramURL = instagramURL;
        this.personalWebSite = personalWebSite;
        this.imageOfTheAd = imageOfTheAd;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getUserID() {
        return uid;
    }

    public void setUserID(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCityOfAd() {
        return cityOfAd;
    }

    public void setCityOfAd(String cityOfAd) {
        this.cityOfAd = cityOfAd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getYouTubeURL() {
        return youTubeURL;
    }

    public void setYouTubeURL(String youTubeURL) {
        this.youTubeURL = youTubeURL;
    }

    public String getFbURL() {
        return fbURL;
    }

    public void setFbURL(String fbURL) {
        this.fbURL = fbURL;
    }

    public String getInstagramURL() {
        return instagramURL;
    }

    public void setInstagramURL(String instagramURL) {
        this.instagramURL = instagramURL;
    }

    public String getPersonalWebSite() {
        return personalWebSite;
    }

    public void setPersonalWebSite(String personalWebSite) {
        this.personalWebSite = personalWebSite;
    }

    public Date getDateAndTimeOfEntry() {
        return dateAndTimeOfEntry;
    }

    public void setDateAndTimeOfEntry(Date dateAndTimeOfEntry) {
        this.dateAndTimeOfEntry = dateAndTimeOfEntry;
    }

    public Date getDateAndTimeOfExpire() {
        return dateAndTimeOfExpire;
    }

    public void setDateAndTimeOfExpire(Date dateAndTimeOfExpire) {
        this.dateAndTimeOfExpire = dateAndTimeOfExpire;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getImageOfTheAd() {
        return imageOfTheAd;
    }

    public void setImageOfTheAd(String imageOfTheAd) {
        this.imageOfTheAd = imageOfTheAd;
    }
}
