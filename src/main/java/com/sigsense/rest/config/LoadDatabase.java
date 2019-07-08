package com.sigsense.rest.config;

import com.sigsense.rest.dao.User;
import com.sigsense.rest.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@Slf4j
public class LoadDatabase {

  @Bean
  public CommandLineRunner initDatabase(UserRepository repository) {
    return args -> {
      log.info("Preloading " + repository.save(new User("Best Candidate", "$2a$10$uM3rim8Zvb1PU8TKDWVs/u4hcRKMWA5tIIBpWYwaZEXaPCs/SFLMq" /*Team Player*/, Collections.singletonList("USER"))));
      log.info("Preloading " + repository.save(new User("Savy Admin", "$2a$10$GK7SOARquGsICDMiLvA8W.rBI4GDjRSkGc6/fdxrv9HYzqHI3zrjC" /*S3cuR3 P4ssW0rD*/, Arrays.asList("USER", "ADMIN"))));
    };
  }
}