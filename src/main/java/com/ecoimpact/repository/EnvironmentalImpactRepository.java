package com.ecoimpact.repository;

import com.ecoimpact.model.EnvironmentalImpact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnvironmentalImpactRepository extends JpaRepository<EnvironmentalImpact, Long> {
} 