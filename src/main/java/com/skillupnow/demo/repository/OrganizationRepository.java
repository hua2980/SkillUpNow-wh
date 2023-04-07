package com.skillupnow.demo.repository;

import com.skillupnow.demo.model.po.Customer;
import com.skillupnow.demo.model.po.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
  Organization findByUsername(String username);
}
