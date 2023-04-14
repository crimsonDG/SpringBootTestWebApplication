package com.security.repository;

import com.core.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Page<User> findAll(Pageable pageable);

    @Query(
            value = "select * from users order by user_id asc",
            nativeQuery = true
    )
    Page<User> ascSorted(Pageable pageable);

    @Query(
            value = "select * from users order by user_id desc",
            nativeQuery = true
    )
    Page<User> descSorted(Pageable pageable);

    @Query(
            value = "select * from users where login = :login",
            nativeQuery = true
    )
    Optional<User> findByLogin(String login);


}
