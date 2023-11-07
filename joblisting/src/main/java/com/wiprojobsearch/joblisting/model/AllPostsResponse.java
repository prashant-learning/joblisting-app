package com.wiprojobsearch.joblisting.model;



import java.util.List;

public class AllPostsResponse {
    private List<PostResponse> posts;

    public AllPostsResponse(List<PostResponse> posts) {
        this.posts = posts;
    }

    // Getters and setters

    public List<PostResponse> getPosts() {
        return posts;
    }

    public void setPosts(List<PostResponse> posts) {
        this.posts = posts;
    }
}
