package com.jwt.Repositories;

import com.jwt.Models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.stream.Stream;

import static org.hibernate.jpa.QueryHints.HINT_CACHEABLE;
import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;

@Repository
public interface UserRepository extends JpaRepository<AppUser,Long> {

    @org.springframework.data.jpa.repository.QueryHints(value = {
            @QueryHint(name = HINT_FETCH_SIZE, value = "50"),
            @QueryHint(name = HINT_CACHEABLE, value = "false")
    })
    @Query("SELECT p FROM AppUser p")
    Stream<AppUser> Users();
    AppUser findByUsername(String username);
}
