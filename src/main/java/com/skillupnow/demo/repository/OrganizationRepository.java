package com.skillupnow.demo.repository;

import com.skillupnow.demo.model.po.Organization;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * OrganizationRepository interface provides methods to interact with the Organization entity in the database.
 * It extends JpaRepository which provides generic CRUD operations on a repository for a specific type.
 *
 * @author Hua Wang
 */
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
  /**
   * Find an organization by its username.
   *
   * @param username the username of the organization to find
   * @return the Organization with the specified username, or null if not found
   */
  Organization findByUsername(String username);
}
