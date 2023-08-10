package com.skillupnow.demo.service;

import com.google.gson.Gson;
import com.skillupnow.demo.exception.SkillUpNowException;
import com.skillupnow.demo.model.dto.CreateUserRequest;
import com.skillupnow.demo.model.po.Organization;
import com.skillupnow.demo.model.po.User;
import com.skillupnow.demo.repository.OrganizationRepository;
import com.skillupnow.demo.repository.UserRepository;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class provides the main services related to managing organizations in the SkillUpNow demo application.
 */
@Service
public class OrganizationService {
  Logger logger = LoggerFactory.getLogger(OrganizationService.class);

  @Autowired
  private OrganizationRepository organizationRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  /**
   * Finds an organization by its username.
   *
   * @param username The username of the organization to be found.
   * @return The found organization with the password field muted.
   * @throws SkillUpNowException If the organization is not found.
   */
  public Organization findByUsername(String username) {
    Organization organization = organizationRepository.findByUsername(username);
    if (organization == null) {
      logger.error("Failed to get organization. Organization not found, username={}", username);
      throw new SkillUpNowException("Organization not found");
    }
    Organization returnOrganization = new Organization();
    BeanUtils.copyProperties(organization, returnOrganization, "password");
    return returnOrganization;
  }

  /**
   * Creates a new organization with the provided request data.
   * This method is transactional, meaning that all database operations are performed within a single transaction.
   *
   * @param createUserRequest The request containing the information needed to create the organization.
   * @return The created organization with the password field muted.
   * @throws SkillUpNowException If the username is already taken.
   */
  @Transactional
  public User createOrganization(CreateUserRequest createUserRequest) throws SkillUpNowException {
    // check if the username is already taken
    if (userRepository.findByUsername(createUserRequest.getUsername()) != null) {
      logger.error("Failed to create organization. Username already taken, username={}", createUserRequest.getUsername());
      throw new SkillUpNowException("Username already taken");
    }

    // save the organization
    Organization organization = new Organization();
    BeanUtils.copyProperties(createUserRequest, organization);
    organization.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));

    // write into database
    User savedUser = organizationRepository.save(organization);

    // return the saved user and mute the password
    User returnInfo = new User();
    BeanUtils.copyProperties(savedUser, returnInfo, "password");

    return returnInfo;
  }

}
