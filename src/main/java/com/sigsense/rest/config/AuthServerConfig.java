package com.sigsense.rest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableResourceServer
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

  private PasswordEncoder passwordEncoder;
  private UserDetailsService userDetailsService;
  private AuthenticationManager authenticationManager;

  public AuthServerConfig(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
    this.passwordEncoder = passwordEncoder;
    this.userDetailsService = userDetailsService;
    this.authenticationManager = authenticationManager;
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    // oauth/check_token endpoint will require client credential
    // as a request header with basic auth username/password
    security.checkTokenAccess("isAuthenticated()").tokenKeyAccess("permitAll()");
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    // authenticationManager bean coming from securty configuration
    // is the class that handles client password authentication
    endpoints.authenticationManager(authenticationManager);
    // adding the UserDetailsService here as well to make refresh_token work
    endpoints.userDetailsService(userDetailsService);
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    // for the purpose of this exersice the client details are hard coded
    // but they should be replaced with a datastore instance and a password encoder
    clients.inMemory()
            .withClient("sigsense-client")
            .secret(passwordEncoder.encode("secret"))
            .scopes("read", "write")
            .authorizedGrantTypes("password", "refresh_token")
            .accessTokenValiditySeconds(1800/* 30min */)
            .refreshTokenValiditySeconds(86400/* 1day */)
            .resourceIds("resource");
  }

}
