package com.Admirablety.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Admirablety.model.User;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
	User findByUsername(String username);
	User findByRole(String role);
}
