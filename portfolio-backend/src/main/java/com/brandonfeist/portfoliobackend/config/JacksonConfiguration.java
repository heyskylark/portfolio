package com.brandonfeist.portfoliobackend.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;

public class JacksonConfiguration {

  /**
   * Creates a custom Jackson mapper. It allows for any flexibility we may need.
   *
   * @return customized ObjectMapper.
   */
  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);

    return objectMapper;
  }
}
