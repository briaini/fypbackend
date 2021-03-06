package com.example.fypbackend.comment;

import com.example.fypbackend.posts.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
    @Query(value = "SELECT * FROM `comments` WHERE user_id = :userID",
            nativeQuery = true)
    List<Comment> findByUserId(@Param("userID") Integer userID);
}
