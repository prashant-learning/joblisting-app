package com.wiprojobsearch.joblisting.model;

public class PostWithAge {

    private Post post;
    private long postAgeInDays;
    private String backgroundColor;

    public PostWithAge(Post post, long postAgeInDays, String backgroundColor) {
        this.post = post;
        this.postAgeInDays = postAgeInDays;
        this.backgroundColor = backgroundColor;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public long getPostAgeInDays() {
        return postAgeInDays;
    }

    public void setPostAgeInDays(long postAgeInDays) {
        this.postAgeInDays = postAgeInDays;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
