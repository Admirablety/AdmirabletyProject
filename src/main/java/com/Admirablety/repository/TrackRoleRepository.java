package com.Admirablety.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Admirablety.model.TrackRole;

@Repository
public interface TrackRoleRepository extends JpaRepository<TrackRole, Long> {
	TrackRole findByRole(String role);
}
