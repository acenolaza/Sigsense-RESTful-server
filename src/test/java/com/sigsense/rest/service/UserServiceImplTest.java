package com.sigsense.rest.service;

import com.sigsense.rest.dao.User;
import com.sigsense.rest.repository.UserRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

  private final List<User> data = Arrays.asList(
          new User("Best Candidate", "$2a$10$uM3rim8Zvb1PU8TKDWVs/u4hcRKMWA5tIIBpWYwaZEXaPCs/SFLMq" /*Team Player*/, Collections.singletonList("USER")),
          new User("Savy Admin", "$2a$10$GK7SOARquGsICDMiLvA8W.rBI4GDjRSkGc6/fdxrv9HYzqHI3zrjC" /*S3cuR3 P4ssW0rD*/, Arrays.asList("USER", "ADMIN")));

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Mock
  UserRepository userRepository;

  @Autowired
  UserServiceImpl userService;

  @Test
  public void getUsernamesTest() {
    when(userRepository.findAll()).thenReturn(data);

    List<String> usernames = userService.getUsernames();

    assertNotNull(usernames);
    assertFalse(usernames.isEmpty());
    assertEquals(data.stream().map(User::getUsername).collect(Collectors.toList()), usernames);
  }

  @Test
  public void loadUserByUsernameTest() {
    doAnswer(invocation -> data.stream().filter(user -> user.getUsername().equals(returnsFirstArg().toString())).findFirst()).when(userRepository).findOneByUsername(anyString());

    assertNotNull(userService.loadUserByUsername("Best Candidate"));
    assertNotNull(userService.loadUserByUsername("Savy Admin"));

    expectedException.expect(UsernameNotFoundException.class);
    expectedException.expectMessage("Invalid username");

    given(userService.loadUserByUsername("Invalid User")).willThrow(new UsernameNotFoundException("Invalid username"));
    given(userService.loadUserByUsername("")).willThrow(new UsernameNotFoundException("Invalid username"));
    given(userService.loadUserByUsername(null)).willThrow(new UsernameNotFoundException("Invalid username"));
  }

}