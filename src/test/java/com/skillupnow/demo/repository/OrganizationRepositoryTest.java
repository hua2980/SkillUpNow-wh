package com.skillupnow.demo.repository;

import com.skillupnow.demo.model.UserType;
import com.skillupnow.demo.model.po.Organization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
public class OrganizationRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private OrganizationRepository organizationRepository;

  @Test
  public void testFindByUsername() {
    // Setup
    Organization organization = new Organization();
    organization.setUsername("testorg");
    organization.setPassword("testpassword");
    organization.setUserType(UserType.ORGANIZATION);
    entityManager.persist(organization);
    entityManager.flush();

    // Normal case
    Organization foundOrganization = organizationRepository.findByUsername("testorg");
    assertNotNull(foundOrganization);
    assertEquals("testorg", foundOrganization.getUsername());

    // Edge case: Non-existent organization
    Organization nonExistentOrganization = organizationRepository.findByUsername("nonexistentorg");
    assertNull(nonExistentOrganization);
  }
}
