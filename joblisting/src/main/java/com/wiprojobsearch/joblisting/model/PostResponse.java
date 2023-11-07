package com.wiprojobsearch.joblisting.model;


import java.util.Date;
import java.util.List;
import java.util.Set;

public class PostResponse {
    private String _id;
    private String profile;
    private String desc;
    private int exp;
    private String[] techs;
    private Date createDate;
    private Set<String> viewedByUserIds;
    private int totalViewsCount;
    private int postWithAge;

    // Constructors, getters, and setters


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String[] getTechs() {
        return techs;
    }

    public void setTechs(String[] techs) {
        this.techs = techs;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Set<String> getViewedByUserIds() {
        return viewedByUserIds;
    }

    public void setViewedByUserIds(Set<String> viewedByUserIds) {
        this.viewedByUserIds = viewedByUserIds;
    }

    public int getTotalViewsCount() {
        return totalViewsCount;
    }

    public void setTotalViewsCount(int totalViewsCount) {
        this.totalViewsCount = totalViewsCount;
    }

    public int getPostWithAge() {
        return postWithAge;
    }

    public void setPostWithAge(int postWithAge) {
        this.postWithAge = postWithAge;
    }

    public PostResponse(String _id, String profile, String desc, int exp, String[] techs, Date createDate, Set<String> viewedByUserIds, int totalViewsCount, int postWithAge) {
        this._id = _id;
        this.profile = profile;
        this.desc = desc;
        this.exp = exp;
        this.techs = techs;
        this.createDate = createDate;
        this.viewedByUserIds = viewedByUserIds;
        this.totalViewsCount = totalViewsCount;
        this.postWithAge = postWithAge;
    }

    public PostResponse(){

    }
}

