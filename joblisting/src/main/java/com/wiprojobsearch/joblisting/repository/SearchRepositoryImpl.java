package com.wiprojobsearch.joblisting.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.convert.MongoConverter;

import com.wiprojobsearch.joblisting.model.Post;

@Component
public class SearchRepositoryImpl implements SearchRepository {

    @Autowired
    MongoClient client;

    @Autowired
    MongoConverter converter;

    @Override
    public Page<Post> findByText(String text, Pageable pageable) {
        // Create a list to hold the matching posts
        final List<Post> posts = new ArrayList<>();

        MongoDatabase database = client.getDatabase("wipro");
        MongoCollection<Document> collection = database.getCollection("JobPost");

        // Perform a text search using the $text operator
        FindIterable<Document> result = collection.find(Filters.text(text))
                .sort(Sorts.ascending("exp")) // Sort by "exp" field
                .skip((int) pageable.getOffset()) // Skip records based on page number
                .limit(pageable.getPageSize()); // Limit the number of results per page

        // Iterate over the results and convert them to Post objects
        result.forEach(doc -> posts.add(converter.read(Post.class, doc)));

        // Count the total number of matching records
        long totalCount = collection.countDocuments(Filters.text(text));

        return new PageImpl<>(posts, pageable, totalCount);
    }
}
