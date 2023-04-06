package com.skillupnow.demo.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillupnow.demo.exception.SkillUpNowException;
import com.skillupnow.demo.model.po.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private AuthenticationManager authenticationManager;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
    setFilterProcessesUrl(SecurityConstants.LOG_IN_URL);
    setPostOnly(true);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    try {
      // parse user from request
      User credentials = new ObjectMapper().readValue(request.getInputStream(), User.class);
      // authenticate user by custom authentication manager

      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(credentials.getUsername(),
              credentials.getPassword(), new ArrayList<>()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {
    /* upon successful authentication, generate a JWT token for the user and add it to the response */

    // Three main parts to be defined for a JWT token
    // 1. payload
    // 2. header
    // 3. algorithm
    String token = JWT.create().withSubject(
            ((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
        .sign(com.auth0.jwt.algorithms.Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));

    // add token to response header
    response.setHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed)
      throws IOException, ServletException {
    // throw custom exception, will be handled by exception handler
    if (failed.getMessage().equals("Bad credentials")) {
      throw new SkillUpNowException("Invalid username or password");
    } else {
      throw new SkillUpNowException("Authentication failed");
    }
  }
}
