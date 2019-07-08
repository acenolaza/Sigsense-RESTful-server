package com.sigsense.rest.repository;

import com.sigsense.rest.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * Find a user by username
   *
   * @param username the user's username
   * @return user which contains the user with the given username or null.
   */
  Optional<User> findOneByUsername(String username);

}
