package com.skillupnow.demo.repository;

import com.skillupnow.demo.model.po.Organization;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
  @Query("SELECT o FROM Organization o WHERE o.username = ?1")
  Organization findByUsername(String username);
}
