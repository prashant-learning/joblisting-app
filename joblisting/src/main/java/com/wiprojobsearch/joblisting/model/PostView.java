package com.wiprojobsearch.joblisting.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "PostView")
public class PostView {

    @Id
    private String id;
    private String userId;
    private List<String> postIds; // List of post IDs
    private boolean viewed;

    public PostView() {
        // Default constructor
    }

    public PostView(String userId, List<String> postIds) {
        this.userId = userId;
        this.postIds = postIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getPostIds() {
        return postIds;
    }

    public void setPostIds(List<String> postIds) {
        this.postIds = postIds;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }
}