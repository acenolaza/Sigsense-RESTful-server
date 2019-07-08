package com.sigsense.rest.webservice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

  @RequestMapping("/")
  public String greetings() {
    return "Hello from Sigsense";
  }

}
