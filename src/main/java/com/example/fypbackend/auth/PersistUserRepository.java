package com.example.fypbackend.auth;

import com.example.fypbackend.comment.Comment;
import com.example.fypbackend.posts.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersistUserRepository extends CrudRepository<PersistUser, Integer> {
    @Override
    Iterable<PersistUser> findAll();

    @Override
    Optional<PersistUser> findById(Integer integer);

    @Query(value = "SELECT ID FROM `users` WHERE USERNAME = :username",
            nativeQuery = true)
    Integer getUserId(@Param("username") String username);



    //    List<PersistUser> findByNameContaining(String name);
}
