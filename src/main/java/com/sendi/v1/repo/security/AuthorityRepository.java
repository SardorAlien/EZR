package com.sendi.v1.repo.security;

import com.sendi.v1.domain.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
