package com.sigsense.rest.webservice;

import com.sigsense.rest.RestApiApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = RestApiApplication.class)
public class UserControllerTest extends BaseControllerTest {

  @Test
  public void givenNoToken_whenGetSecureRequest_thenUnauthorized() throws Exception {
    mockMvc.perform(get("/rest/user").contextPath("/rest"))
            .andExpect(status().isUnauthorized());
  }

  @Test
  public void givenInvalidRole_whenGetSecureAdminRequest_thenForbidden() throws Exception {
    String accessToken = obtainAccessToken("Best Candidate", "Team Player");
    mockMvc.perform(get("/rest/user").contextPath("/rest")
            .header("Authorization", "Bearer " + accessToken))
            .andExpect(status().isForbidden());
  }

  @Test
  public void givenValidRole_whenGetSecureAdminRequest_thenOk() throws Exception {
    String accessToken = obtainAccessToken("Savy Admin", "S3cuR3 P4ssW0rD");
    mockMvc.perform(get("/rest/user").contextPath("/rest")
            .header("Authorization", "Bearer " + accessToken))
            .andExpect(status().isOk());
  }

}