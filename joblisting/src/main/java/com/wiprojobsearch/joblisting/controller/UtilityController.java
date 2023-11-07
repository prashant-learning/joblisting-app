package com.wiprojobsearch.joblisting.controller;

import com.wiprojobsearch.joblisting.model.Post;
import com.wiprojobsearch.joblisting.model.PostWithAge;
import com.wiprojobsearch.joblisting.repository.PostRepository;
import com.wiprojobsearch.joblisting.utility.PostUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@RestController
@RequestMapping("/api/utility")
public class UtilityController {

    @Autowired
    private PostRepository postRepository;




    //Both Red and Green
    @GetMapping("/postsWithAge")
    public List<PostWithAge> getPostsWithAge() {
        List<Post> posts = postRepository.findAll(); // Fetch all posts

        List<PostWithAge> postsWithAge = new ArrayList<>();
        for (Post post : posts) {
            long postAgeInDays = calculatePostAgeInDays(post.getCreateDate());
            String backgroundColor = postAgeInDays > 30 ? "red" : "green";
            postsWithAge.add(new PostWithAge(post, postAgeInDays, backgroundColor));
        }

        return postsWithAge;
    }



    @PostMapping("/createPostWithCustomDate")
    public ResponseEntity<String> createPostWithCustomDate() {
        // Create a Calendar instance
        Calendar calendar = Calendar.getInstance();

        // Set the calendar to a date more than 30 days ago (e.g., subtracting 40 days)
        calendar.add(Calendar.DAY_OF_YEAR, -40);

        // Get the Date object representing the custom createDate
        Date customCreateDate = calendar.getTime();

        // Create a new Post object
        Post post = new Post();
        post.setProfile("Custom Profile");
        post.setDesc("Custom Description");
        // Set other fields as needed
        post.setCreateDate(customCreateDate); // Set the custom createDate

        // Save the post to the database using the PostRepository
        postRepository.save(post);

        return ResponseEntity.ok("Post created with custom date.");
    }

    private Date thirtyDaysAgo() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -30);
        return calendar.getTime();
    }

    private long calculatePostAgeInDays(Date createDate) {
        long millisecondsPerDay = 24 * 60 * 60 * 1000;
        Date currentDate = new Date();
        long ageInMillis = currentDate.getTime() - createDate.getTime();
        return ageInMillis / millisecondsPerDay;
    }
}
