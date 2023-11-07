package com.wiprojobsearch.joblisting.repository;

import com.wiprojobsearch.joblisting.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/*public interface SearchRepository {

    List<Post> findByText(String text);

}*/

public interface SearchRepository {

    Page<Post> findByText(String text, Pageable pageable);
}
