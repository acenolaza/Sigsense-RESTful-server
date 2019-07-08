package com.sigsense.rest.webservice;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BaseControllerTest {

  protected MockMvc mockMvc;
  @Autowired
  private WebApplicationContext wac;
  @Autowired
  private FilterChainProxy springSecurityFilterChain;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
            .addFilter(springSecurityFilterChain).build();
  }

  protected String obtainAccessToken(String username, String password) throws Exception {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("username", username);
    params.add("password", password);
    params.add("grant_type", "password");

    ResultActions result = mockMvc.perform(post("/rest/oauth/token")
            .contextPath("/rest")
            .params(params)
            .with(httpBasic("sigsense-client", "secret"))
            .accept("application/json;charset=UTF-8"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"));

    String resultString = result.andReturn().getResponse().getContentAsString();

    JacksonJsonParser jsonParser = new JacksonJsonParser();
    return jsonParser.parseMap(resultString).get("access_token").toString();
  }

}
