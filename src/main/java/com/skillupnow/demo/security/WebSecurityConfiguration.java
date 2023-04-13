package com.skillupnow.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
  private UserDetailServiceImpl userDetailService;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public WebSecurityConfiguration(UserDetailServiceImpl userDetailService,
      BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userDetailService = userDetailService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable().authorizeRequests()
        .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
        .antMatchers("/customer/**", "/cart/**").hasAuthority("CUSTOMER") // 只允许具有 ROLE_CUSTOMER 权限的用户访问 /customer 下的资源
        .antMatchers("/organization/**").hasAuthority("ORGANIZATION") // 只允许具有 ROLE_COMPANY 权限的用户访问 /company 下的资源
        .anyRequest().authenticated()
        .and()
        .addFilter(new JWTAuthenticationFilter(super.authenticationManager(), userDetailService))
        .addFilter(new JWTAuthenticationVerificationFilter(super.authenticationManager()))
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.exceptionHandling()
        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
    // de-comment this line to disable security
    // http.cors().and().csrf().disable().authorizeRequests()
    //     .anyRequest().permitAll();
  }

  @Override
  @Bean("authenticationManager")
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
     auth.userDetailsService(userDetailService).passwordEncoder(bCryptPasswordEncoder);
  }
}
