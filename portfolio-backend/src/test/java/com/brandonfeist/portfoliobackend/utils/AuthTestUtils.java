package com.brandonfeist.portfoliobackend.utils;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class AuthTestUtils {

  /**
   * Test utility used to generate an OAuth2 token for secured endpoints.
   *
   * @param mockMvc mock server that we will be making the oauth token call to.
   * @param username username of the user.
   * @param password password of the user.
   * @return a String representing an OAuth2 token.
   */
  public static String obtainAccessToken(MockMvc mockMvc, String username, String password)
      throws Exception {

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("grant_type", "password");
    params.add("client_id", "testClient");
    params.add("username", username);
    params.add("password", password);

    ResultActions result = mockMvc.perform(post("/oauth/token")
        .params(params)
        .with(httpBasic("testClient","testpassword"))
        .accept("application/json;charset=UTF-8"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"));

    String resultString = result.andReturn().getResponse().getContentAsString();

    JacksonJsonParser jsonParser = new JacksonJsonParser();
    return jsonParser.parseMap(resultString).get("access_token").toString();
  }
}
