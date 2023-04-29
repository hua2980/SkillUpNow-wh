package com.skillupnow.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * The main entry point of the SkillUpNow demo application.
 * The class enables JPA repositories and entity scanning, and excludes the default
 * Spring Boot security autoconfiguration.
 */
@EnableJpaRepositories("com.skillupnow.demo.repository")
@EntityScan("com.skillupnow.demo.model.po")
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class DemoApplication {
  /**
   * Provides an instance of BCryptPasswordEncoder to be used as a bean throughout the application.
   *
   * @return An instance of BCryptPasswordEncoder.
   */
  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder(){
    return new BCryptPasswordEncoder();
  }

  /**
   * The main method of the SkillUpNow demo application, responsible for starting the application.
   *
   * @param args Command line arguments.
   */
  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

}
