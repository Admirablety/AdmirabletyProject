package com.AdmirabletyProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AdmirabletyProject.model.Tracking;

@Repository
public interface TrackingRepository extends JpaRepository<Tracking, Long> {
	Tracking findByRole(String role);
}
