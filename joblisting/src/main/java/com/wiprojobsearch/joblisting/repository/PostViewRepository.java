package com.wiprojobsearch.joblisting.repository;

import com.wiprojobsearch.joblisting.model.PostView;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PostViewRepository extends MongoRepository<PostView, String> {
    Optional<PostView> findByUserId(String userId);
}
