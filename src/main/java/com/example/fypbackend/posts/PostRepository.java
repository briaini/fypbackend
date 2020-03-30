package com.example.fypbackend.posts;

import com.example.fypbackend.auth.PersistUser;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface PostRepository extends CrudRepository<Post, Integer> {

    @Query(value = "SELECT * FROM `posts` INNER JOIN `users_posts` ON users_posts.post_id = posts.id WHERE users_posts.user_id = :userID",
    nativeQuery = true)
    Iterable<Post> findUserPosts(@Param("userID") Integer userID);

    @Override
    Iterable<Post> findAll();
}