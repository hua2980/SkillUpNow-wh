package com.skillupnow.demo.service;

import com.skillupnow.demo.exception.SkillUpNowException;
import com.skillupnow.demo.model.dto.CreateUserRequest;
import com.skillupnow.demo.model.po.Organization;
import com.skillupnow.demo.model.po.User;
import com.skillupnow.demo.repository.OrganizationRepository;
import com.skillupnow.demo.repository.UserRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrganizationService {
  @Autowired
  private OrganizationRepository organizationRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public Organization findById(Long id) {
    Organization organization = organizationRepository.findById(id).orElse(null);
    if (organization == null) {
      throw new SkillUpNowException("Organization not found");
    }
    Organization returnOrganization = new Organization();
    BeanUtils.copyProperties(organization, returnOrganization, "password");
    return returnOrganization;
  }

  @Transactional
  public User createOrganization(CreateUserRequest createUserRequest) throws SkillUpNowException {
    // check if the username is already taken
    if (userRepository.findByUsername(createUserRequest.getUsername()) != null) {
      throw new SkillUpNowException("Username already taken");
    }

    // save the organization
    Organization organization = new Organization();
    BeanUtils.copyProperties(createUserRequest, organization);
    organization.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));

    // write into database
    User savedUser = organizationRepository.save(organization);
    if (savedUser.getId() == null) {
      throw new SkillUpNowException("User not saved");
    }

    // return the saved user and mute the password
    User returnInfo = new User();
    BeanUtils.copyProperties(savedUser, returnInfo, "password");

    return returnInfo;
  }

}
