package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dto.Incident;

public interface IncidentRepository extends JpaRepository<Incident , Long>{

	List<Incident> findByUserName(String username);

	boolean existsByIncidentId(String incidentId);

}
