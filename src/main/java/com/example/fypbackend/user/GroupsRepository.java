package com.example.fypbackend.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GroupsRepository extends CrudRepository<Groups, Integer> {
    @Override
    Iterable<Groups> findAll();

    @Override
    Optional<Groups> findById(Integer integer);

//    @Query(value = "SELECT ID FROM `users` WHERE USERNAME = :username",
//            nativeQuery = true)
//    Integer getUserId(@Param("username") String username);

    //    List<PersistUser> findByNameContaining(String name);
}
