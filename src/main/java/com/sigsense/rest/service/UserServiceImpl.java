package com.sigsense.rest.service;

import com.sigsense.rest.dao.User;
import com.sigsense.rest.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<String> getUsernames() {
    return userRepository.findAll().stream().map(User::getUsername).collect(Collectors.toList());
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findOneByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Invalid username"));
  }

}
