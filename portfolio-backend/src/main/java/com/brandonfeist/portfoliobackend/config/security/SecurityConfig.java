package com.brandonfeist.portfoliobackend.config.security;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final DataSource dataSource;

  private PasswordEncoder passwordEncoder;
  private UserDetailsService userDetailsService;

  @Autowired
  public SecurityConfig(final DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService())
        .passwordEncoder(passwordEncoder());
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  public void configure(WebSecurity web) {
    web
        .debug(true)
        .ignoring()
        .antMatchers(HttpMethod.OPTIONS)
        .antMatchers("/assets/**", "/swagger-ui.html", "/webjars/**", "/swagger-resources/**");
  }

    @Override
  public void configure(HttpSecurity http) throws Exception {
      http
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
          .and()

          .authorizeRequests()
          .antMatchers("/login").permitAll()
          .anyRequest().authenticated()
          .and()

          .formLogin().permitAll()
          .and()

          .logout().permitAll()
          .and()

          .csrf().disable();
  }

  /**
   * Custom password encoder that can encode in bcrypt, pbkdf2, and scrypt.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    if (passwordEncoder == null) {
      String idForEncode = "bcrypt";
      Map<String, PasswordEncoder> encoders = new HashMap<>();
      encoders.put(idForEncode, new BCryptPasswordEncoder());
      encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
      encoders.put("scrypt", new SCryptPasswordEncoder());
      passwordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);
    }
    return passwordEncoder;
  }

  /**
   * Sets the data source (jbdc database) for the user details service.
   */
  @Bean
  public UserDetailsService userDetailsService() {
    if (userDetailsService == null) {
      userDetailsService = new JdbcDaoImpl();
      ((JdbcDaoImpl) userDetailsService).setDataSource(dataSource);
    }
    return userDetailsService;
  }
}
