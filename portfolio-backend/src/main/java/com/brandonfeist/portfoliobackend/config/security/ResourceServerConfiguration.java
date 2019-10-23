package com.brandonfeist.portfoliobackend.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@Configuration
@EnableResourceServer
@EnableConfigurationProperties(SecurityProperties.class)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
  private static final String ROOT_PATTERN = "/**";
  private static final String RESOURCE_ID = "resource_id";

  private final TokenStore tokenStore;

  @Autowired
  public ResourceServerConfiguration(final TokenStore tokenStore) {
    this.tokenStore = tokenStore;
  }

  @Override
  public void configure(final ResourceServerSecurityConfigurer resources) {
    resources.tokenStore(tokenStore);
    resources.resourceId(RESOURCE_ID).stateless(false);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http
        .requestMatchers()
            .requestMatchers(
                new NegatedRequestMatcher(
                    new OrRequestMatcher(
                        new AntPathRequestMatcher("/login"),
                        new AntPathRequestMatcher("/logout"),
                        new AntPathRequestMatcher("/oauth/authorize"),
                        new AntPathRequestMatcher("/oauth/confirm_access")
                    )
                )
            )
        .and()

        .authorizeRequests()
        .antMatchers(HttpMethod.GET, ROOT_PATTERN).permitAll()
        .antMatchers(HttpMethod.POST, ROOT_PATTERN).access("#oauth2.hasScope('write')")
        .antMatchers(HttpMethod.PATCH, ROOT_PATTERN).access("#oauth2.hasScope('write')")
        .antMatchers(HttpMethod.PUT, ROOT_PATTERN).access("#oauth2.hasScope('write')")
        .antMatchers(HttpMethod.DELETE, ROOT_PATTERN).access("#oauth2.hasScope('write')");
  }
}
