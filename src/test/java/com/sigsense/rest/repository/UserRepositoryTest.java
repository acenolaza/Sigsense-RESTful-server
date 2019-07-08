package com.sigsense.rest.repository;

import com.sigsense.rest.RestApiApplication;
import com.sigsense.rest.config.LoadDatabase;
import com.sigsense.rest.dao.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
//@SpringBootTest(classes = RestApiApplication.class)
public class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Before
  public void setUp() throws Exception {
    new LoadDatabase().initDatabase(userRepository).run();
  }

  @Test
  public void givenValidUsername_whenFindOneByUsername_thenUserExist() {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    Optional<User> user = userRepository.findOneByUsername("Best Candidate");
    assertTrue(user.isPresent());
    assertTrue(passwordEncoder.matches("Team Player", user.get().getPassword()));
    assertEquals(user.get().getRoles().size(), 1);

    Optional<User> admin = userRepository.findOneByUsername("Savy Admin");
    assertTrue(admin.isPresent());
    assertTrue(passwordEncoder.matches("S3cuR3 P4ssW0rD", admin.get().getPassword()));
    assertEquals(admin.get().getRoles().size(), 2);

    assertFalse(userRepository.findOneByUsername("Invalid User").isPresent());
  }

  @Test
  public void givenInvalidUsername_whenFindOneByUsername_thenUserDoesNotExist() {
    assertFalse(userRepository.findOneByUsername("Invalid User").isPresent());
  }

}