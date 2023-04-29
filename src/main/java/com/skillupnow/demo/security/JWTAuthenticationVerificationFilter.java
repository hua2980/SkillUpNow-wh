package com.skillupnow.demo.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

/**
 * JWTAuthenticationVerificationFilter is a custom authentication filter for verifying the
 * JWT token in the incoming request. It extends the BasicAuthenticationFilter provided
 * by Spring Security.
 *
 * @author Hua Wang
 */

@Component
public class JWTAuthenticationVerificationFilter extends BasicAuthenticationFilter {

  /**
   * Constructs a new JWTAuthenticationVerificationFilter with the given authentication manager.
   *
   * @param authManager the authentication manager
   */
  public JWTAuthenticationVerificationFilter(AuthenticationManager authManager) {
    super(authManager);
  }

  /**
   * Filters the incoming request and verifies the JWT token in the header. If the token is valid,
   * it sets the authentication in the security context.
   *
   * @param req the HttpServletRequest
   * @param res the HttpServletResponse
   * @param chain the FilterChain
   * @throws IOException if an input or output error occurs
   * @throws ServletException if a servlet-specific error occurs
   */
  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    String header = req.getHeader(SecurityConstants.HEADER_STRING);

    if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
      chain.doFilter(req, res);
      return;
    }

    UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(req, res);
  }

  /**
   * Retrieves the authentication token from the request header, decodes the JWT token,
   * and creates a new UsernamePasswordAuthenticationToken.
   *
   * @param req the HttpServletRequest
   * @return the UsernamePasswordAuthenticationToken containing the user details and authorities
   */
  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
    String token = req.getHeader(SecurityConstants.HEADER_STRING);
    if (token != null) {
      DecodedJWT decodedJWT = JWT.require(HMAC512(SecurityConstants.SECRET.getBytes())).build()
          .verify(token.replace(SecurityConstants.TOKEN_PREFIX, ""));
      String user = decodedJWT.getSubject();
      String[] authoritiesArray = decodedJWT.getClaim("roles").asString().split(",");
      List<GrantedAuthority> authorities = Arrays.stream(authoritiesArray)
          .map(SimpleGrantedAuthority::new)
          .collect(Collectors.toList());
      if (user != null) {
        return new UsernamePasswordAuthenticationToken(user, null, authorities);
      }
      return null;
    }
    return null;
  }

}
