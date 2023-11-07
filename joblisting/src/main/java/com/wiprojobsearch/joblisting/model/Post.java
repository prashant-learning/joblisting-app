package com.wiprojobsearch.joblisting.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "JobPost")
public class Post {

    private String _id;
    private String profile;
    private String desc;
    private int exp;
    private String techs[];
    private Date date;
    private int totalViewsCount;
    private Date createDate;

    private Set<String> viewedByUserIds = new HashSet<>();


    public Post() {
    }



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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate	= createDate;
    }

    public int getTotalViewsCount() {
        return totalViewsCount;
    }

    public void setTotalViewsCount(int totalViewsCount) {
        this.totalViewsCount = totalViewsCount;
    }


    public Set<String> getViewedByUserIds() {
        return viewedByUserIds;
    }

    public void setViewedByUserIds(Set<String> viewedByUserIds) {
        this.viewedByUserIds = viewedByUserIds;
    }
}