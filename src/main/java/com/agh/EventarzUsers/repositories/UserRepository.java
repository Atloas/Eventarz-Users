package com.agh.EventarzUsers.repositories;

import com.agh.EventarzUsers.model.User;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
// TODO: I don't actually use those methods much so the Retry and CircuitBreaker probably dn't really work?
@Retry(name = "UserRepositoryRetry")
@CircuitBreaker(name = "UserRepositoryCircuitBreaker")
public interface UserRepository extends CrudRepository<User, String> {

    User findByUsername(String username);

    List<User> findByUsernameLikeIgnoreCase(String regex);

    @Transactional
    void deleteByUsername(String username);

    @Query("SELECT passwordHash FROM user u WHERE u.username = :username")
    String findPasswordHashOf(String username);
}
