package com.wiprojobsearch.joblisting.controller;

import com.wiprojobsearch.joblisting.model.AllPostsResponse;
import com.wiprojobsearch.joblisting.model.Post;
import com.wiprojobsearch.joblisting.model.PostResponse;
import com.wiprojobsearch.joblisting.model.PostView;
import com.wiprojobsearch.joblisting.repository.PostRepository;
import com.wiprojobsearch.joblisting.repository.PostViewRepository;
import com.wiprojobsearch.joblisting.repository.SearchRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PostController
{

    @Autowired
    PostRepository repo;

    @Autowired
    SearchRepository srepo;

    @ApiIgnore
    @RequestMapping(value="/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }



    @GetMapping("/allPosts")
    @CrossOrigin
    public AllPostsResponse getAllPosts() {
        Sort sortByDateDesc = Sort.by(Sort.Order.desc("date"));
        List<Post> allPosts = repo.findAll(sortByDateDesc);

        List<PostResponse> posts = allPosts.stream()
                .map(this::convertToPostResponse)
                .collect(Collectors.toList());

        return new AllPostsResponse(posts);
    }

    private PostResponse convertToPostResponse(Post post) {
        PostResponse response = new PostResponse();
        response.set_id(post.get_id());
        response.setProfile(post.getProfile());
        response.setDesc(post.getDesc());
        response.setExp(post.getExp());
        response.setTechs(post.getTechs());
        response.setCreateDate(post.getCreateDate());
        response.setViewedByUserIds(post.getViewedByUserIds());

        // Calculate totalViewsCount
        response.setTotalViewsCount(post.getViewedByUserIds().size());

        // Calculate postWithAge
        response.setPostWithAge(calculatePostAgeInDays(post.getCreateDate()));

        return response;
    }

    private int calculatePostAgeInDays(Date createDate) {
        // Calculate the time difference between the createDate and current date in days
        long millisecondsPerDay = 24 * 60 * 60 * 1000;
        Date currentDate = new Date();
        long ageInMillis = currentDate.getTime() - createDate.getTime();
        return (int) (ageInMillis / millisecondsPerDay);
    }




    @GetMapping("/posts/{text}")
    @CrossOrigin
    public Page<Post> search(@PathVariable String text,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "3") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return srepo.findByText(text, pageable);
    }


    @PostMapping("/post")
    @CrossOrigin
    public Post addPost(@RequestBody Post post)
    {
        return repo.save(post);
    }

    @DeleteMapping("/post/{postId}")
    @CrossOrigin
    public ResponseEntity<String> deletePost(@PathVariable String postId) {
        try {
            // Check if the post exists
            Optional<Post> existingPost = repo.findById(postId);
            if (existingPost.isPresent()) {
                // Post exists, delete it
                repo.deleteById(postId);
                return ResponseEntity.ok("Post deleted successfully");
            } else {
                // Post not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
            }
        } catch (Exception e) {
            // Handle exceptions, e.g., database errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting post");
        }
    }



    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostViewRepository postViewRepository;



    @PostMapping("/view/{postId}")
    public ResponseEntity<?> viewPost(@PathVariable String postId, @RequestParam String userId) {
        try {
            Optional<Post> existingPost = repo.findById(postId);

            if (existingPost.isPresent()) {
                Post post = existingPost.get();
                Set<String> viewedByUserIds = post.getViewedByUserIds();

                if (!viewedByUserIds.contains(userId)) {
                    // User has not viewed this post before
                    post.setTotalViewsCount(post.getTotalViewsCount() + 1);
                    viewedByUserIds.add(userId);
                    repo.save(post);

                    // Save the view in PostView collection
                    saveViewInPostViewCollection(postId, userId);
                }

                return ResponseEntity.ok("Post viewed successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error viewing post");
        }
    }

    private void saveViewInPostViewCollection(String postId, String userId) {
        try {
            Optional<PostView> existingPostView = postViewRepository.findByUserId(userId);

            if (existingPostView.isPresent()) {
                PostView postView = existingPostView.get();
                List<String> postIds = postView.getPostIds();

                if (!postIds.contains(postId)) {
                    postIds.add(postId);
                }

                postViewRepository.save(postView);
            } else {
                PostView newPostView = new PostView(userId, Arrays.asList(postId));
                postViewRepository.save(newPostView);
            }
        } catch (Exception e) {
            // Handle exceptions, e.g., database errors
        }
    }

    @GetMapping("/post/total-views/{postId}")
    public ResponseEntity<?> getTotalViewsForPost(@PathVariable String postId) {
        try {
            // Find the post by ID
            Optional<Post> existingPost = repo.findById(postId);

            if (existingPost.isPresent()) {
                // Post found, retrieve the total views count
                int totalViews = existingPost.get().getTotalViewsCount();
                return ResponseEntity.ok("Total views for post with ID " + postId + ": " + totalViews);
            } else {
                // Post not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
            }
        } catch (Exception e) {
            // Handle exceptions, e.g., database errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting total views for post");
        }
    }

    @Autowired
    private MongoTemplate mongoTemplate;


    @GetMapping("/mostViewedPost")
    public ResponseEntity<Post> getMostViewedPost() {
        Post mostViewedPost = postRepository.findTopByOrderByTotalViewsCountDesc();
        if (mostViewedPost != null) {
            return ResponseEntity.ok(mostViewedPost);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
