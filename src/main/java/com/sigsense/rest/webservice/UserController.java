package com.sigsense.rest.webservice;

import com.sigsense.rest.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping(method = GET)
  @PreAuthorize("hasAuthority('ADMIN')")
  public List<String> getUsernames() {
    return userService.getUsernames();
  }

  @RequestMapping(value = "/me", method = GET)
  public Principal user(Principal principal) {
    return principal;
  }

}
