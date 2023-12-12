package com.fabrice.imdbclone.repositories;

import com.fabrice.imdbclone.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    //create optional custom method for finding user by email
    Optional<User> findByEmail(String email);
}
