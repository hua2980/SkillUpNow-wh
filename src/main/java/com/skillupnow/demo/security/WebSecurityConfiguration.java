package com.skillupnow.demo.security;

import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.resource.PathResourceResolver;

/**
 * This class provides the main configuration for the web security in the SkillUpNow demo application.
 * It extends the {@link WebSecurityConfigurerAdapter} class to customize the security configuration.
 */
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
  private UserDetailServiceImpl userDetailService;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  /**
   * Constructs a new WebSecurityConfiguration instance with the provided user detail service and password encoder.
   *
   * @param userDetailService     The user detail service used for user authentication.
   * @param bCryptPasswordEncoder The password encoder used to hash and verify user passwords.
   */
  public WebSecurityConfiguration(UserDetailServiceImpl userDetailService,
      BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userDetailService = userDetailService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  /**
   * Configures the HTTP security for the application, such as CORS, CSRF, authentication and authorization rules.
   *
   * @param http The HttpSecurity object to configure.
   * @throws Exception If an error occurs during the configuration.
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable().authorizeRequests()
        .antMatchers("/login.html", "/signup.html", "/home.html", "/user/**", "/course/**", "/course.html","/cart.html", "/customer.html").permitAll()
        .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
        .antMatchers("/customer/**", "/cart/**", "/course/**").hasAuthority("CUSTOMER") // 只允许具有 ROLE_CUSTOMER 权限的用户访问 /customer 下的资源
        .antMatchers("/organization/**").hasAuthority("ORGANIZATION") // 只允许具有 ROLE_COMPANY 权限的用户访问 /company 下的资源
        .antMatchers("/static/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .addFilter(new JWTAuthenticationFilter(super.authenticationManager()))
        .addFilter(new JWTAuthenticationVerificationFilter(super.authenticationManager()))
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.headers()
        .contentSecurityPolicy("default-src 'self' 'unsafe-inline'; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://unpkg.com")
        .and()
        .contentTypeOptions();

    http.exceptionHandling()
        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
    // de-comment this line to disable security
    // http.cors().and().csrf().disable().authorizeRequests()
    //     .anyRequest().permitAll();
  }

  /**
   * Configures the web security for the application, such as static resource handling.
   *
   * @param web The WebSecurity object to configure.
   */
  @Override
  public void configure(WebSecurity web) {
    web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    web.ignoring().antMatchers("/css/**");
    web.ignoring().antMatchers("/js/**");
    web.ignoring().antMatchers("/image/**");
  }

  /**
   * Provides the authentication manager bean used for authentication purposes.
   *
   * @return The authentication manager bean.
   * @throws Exception If an error occurs during the creation of the authentication manager bean.
   */
  @Override
  @Bean("authenticationManager")
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  /**
   * Configures the authentication manager builder with the user detail service and password encoder.
   *
   * @param auth The AuthenticationManagerBuilder object to configure.
   * @throws Exception If an error occurs during the configuration.
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
     auth.userDetailsService(userDetailService).passwordEncoder(bCryptPasswordEncoder);
  }
}
