package com.Admirablety.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Admirablety.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByRole(String role);
}
