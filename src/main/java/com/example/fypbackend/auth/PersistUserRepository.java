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

    @Query(value = "SELECT * FROM `users` WHERE role = \"PATIENT\" AND id NOT IN (SELECT id FROM `users` INNER JOIN `users_groups` ON users_groups.user_id = users.id where role = \"PATIENT\" and users_groups.groups_id in ( SELECT groups_id FROM `users_groups` INNER JOIN `groups` ON groups.id = users_groups.groups_id WHERE is_mdt = true))\n",
            nativeQuery = true)
    List<PersistUser> getAllUnassignedPatients();

    @Query(value = "SELECT * FROM `users` INNER JOIN `users_groups` ON users_groups.user_id = users.id where role = \"PATIENT\" and users_groups.groups_id in ( SELECT groups_id FROM `users_groups` INNER JOIN `groups` ON groups.id = users_groups.groups_id WHERE is_mdt = true) ",
            nativeQuery = true)
    List<PersistUser> getAllAssignedPatients();


    @Query(value = "SELECT * FROM `users` INNER JOIN `users_groups` ON users_groups.user_id = users.id where role = \"PATIENT\" and users_groups.groups_id in ( SELECT groups_id FROM `users_groups` INNER JOIN `groups` ON groups.id = users_groups.groups_id WHERE user_id = :id AND is_mdt = true) ",
            nativeQuery = true)
    List<PersistUser> getPatients(@Param("id") Integer id);

}
