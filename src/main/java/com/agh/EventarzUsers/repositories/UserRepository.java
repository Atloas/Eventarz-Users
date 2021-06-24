package com.agh.EventarzUsers.repositories;

import com.agh.EventarzUsers.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    User findByUsername(String username);

    List<User> findByUsernameLikeIgnoreCase(String regex);

    void deleteByUsername(String username);

    @Query("SELECT passwordHash FROM user u WHERE u.username = :username")
    String findPasswordHashOf(String username);
}
