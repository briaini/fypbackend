package com.example.fypbackend.user;

import com.example.fypbackend.auth.Patient;
import com.example.fypbackend.auth.PersistUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GroupsRepository extends CrudRepository<Groups, Integer> {
    @Override
    Iterable<Groups> findAll();

    @Override
    Optional<Groups> findById(Integer integer);

    @Query(value = "SELECT groups_id FROM `users_groups` INNER JOIN `groups` ON groups.id = users_groups.groups_id WHERE user_id = :id AND is_mdt = true",
            nativeQuery = true)
    Integer getGroupByUserId(@Param("id") Integer id);


    @Query(value = "SELECT groups_id FROM `users_groups` INNER JOIN `groups` ON groups.id = users_groups.groups_id WHERE user_id = :id AND is_mdt = true",
        nativeQuery = true)
    List<Integer> getProsMdtIds(@Param("id") Integer id);
}
