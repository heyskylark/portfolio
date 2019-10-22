package com.brandonfeist.portfoliobackend.config.security;

import static java.nio.charset.StandardCharsets.UTF_8;

import io.micrometer.core.instrument.util.IOUtils;
import java.io.IOException;
import java.security.KeyPair;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
@EnableAuthorizationServer
@EnableConfigurationProperties(SecurityProperties.class)
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
  private final DataSource dataSource;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final SecurityProperties securityProperties;
  private final UserDetailsService userDetailsService;

  private JwtAccessTokenConverter jwtAccessTokenConverter;
  private TokenStore tokenStore;

  /**
   * Authorization Server Configuration dependencies constructor.
   */
  @Autowired
  public AuthorizationServerConfiguration(final DataSource dataSource,
                                          final PasswordEncoder passwordEncoder,
                                          final AuthenticationManager authenticationManager,
                                          final SecurityProperties securityProperties,
                                          final UserDetailsService userDetailsService) {
    this.dataSource = dataSource;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.securityProperties = securityProperties;
    this.userDetailsService = userDetailsService;
  }

  /**
   * Token Store, does not actually persist the token after sending to the client.
   *
   * @return a JwtTokenStore with a jwt token converter.
   */
  @Bean
  public TokenStore tokenStore() {
    if (tokenStore == null) {
      tokenStore = new JwtTokenStore(jwtAccessTokenConverter());
    }
    return tokenStore;
  }

  @Bean
  @Primary
  public DefaultTokenServices tokenServices(final TokenStore tokenStore,
                                            final ClientDetailsService clientDetailsService) {
    DefaultTokenServices tokenServices = new DefaultTokenServices();
    tokenServices.setTokenStore(tokenStore);
    tokenServices.setClientDetailsService(clientDetailsService);
    tokenServices.setAuthenticationManager(this.authenticationManager);
    return tokenServices;
  }

  /**
   * Creates a jwt converter converting the token using the keyPair and public key.
   */
  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    if (jwtAccessTokenConverter != null) {
      return jwtAccessTokenConverter;
    }

    SecurityProperties.JwtProperties jwtProperties = securityProperties.getJwt();
    KeyPair keyPair = keyPair(jwtProperties, keyStoreKeyFactory(jwtProperties));

    jwtAccessTokenConverter = new JwtAccessTokenConverter();
    jwtAccessTokenConverter.setKeyPair(keyPair);
    jwtAccessTokenConverter.setVerifierKey(getPublicKeyAsString());

    return jwtAccessTokenConverter;
  }

  @Override
  public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
    // TODO Figure out if were going ot use auth code or password with no client_secret
    clients.jdbc(this.dataSource);
  }

  @Override
  public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
    endpoints.authenticationManager(this.authenticationManager)
        .accessTokenConverter(jwtAccessTokenConverter())
        .userDetailsService(this.userDetailsService)
        .tokenStore(tokenStore());
  }

  @Override
  public void configure(final AuthorizationServerSecurityConfigurer oauthServer) {
    oauthServer
        .passwordEncoder(this.passwordEncoder)
        .tokenKeyAccess("permitAll()")
        .checkTokenAccess("isAuthenticated()")
        .allowFormAuthenticationForClients();
  }

  private KeyPair keyPair(SecurityProperties.JwtProperties jwtProperties,
                          KeyStoreKeyFactory keyStoreKeyFactory) {
    return keyStoreKeyFactory.getKeyPair(jwtProperties.getKeyPairAlias(),
        jwtProperties.getKeyPairPassword().toCharArray());
  }

  private KeyStoreKeyFactory keyStoreKeyFactory(SecurityProperties.JwtProperties jwtProperties) {
    return new KeyStoreKeyFactory(jwtProperties.getKeyStore(),
        jwtProperties.getKeyStorePassword().toCharArray());
  }

  private String getPublicKeyAsString() {
    try {
      return IOUtils.toString(securityProperties.getJwt().getPublicKey().getInputStream(), UTF_8);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
