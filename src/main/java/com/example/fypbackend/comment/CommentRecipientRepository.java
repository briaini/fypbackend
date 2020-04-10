package com.example.fypbackend.comment;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CommentRecipientRepository extends CrudRepository<CommentRecipient, Integer> {
    @Query(value = "SELECT * FROM `commentrecipient` WHERE comment_grouprecipient = :id",
            nativeQuery = true)
    CommentRecipient getByGroupId(@Param("id") Integer id);
}
