package com.core.repository;

import com.core.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(
            value = "select * from users order by user_id asc",
            nativeQuery = true
    )
    Iterable<User> ascSorted();

    @Query(
            value = "select * from users order by user_id desc",
            nativeQuery = true
    )
    Iterable<User> descSorted();

    @Query(
            value = "select * from users where login = :login",
            nativeQuery = true
    )
    User findByLogin(@Param("login") String login);


}
