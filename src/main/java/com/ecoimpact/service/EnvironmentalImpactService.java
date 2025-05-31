package com.ecoimpact.service;

import com.ecoimpact.model.EnvironmentalImpact;
import java.util.List;
import java.util.Optional;

public interface EnvironmentalImpactService {
    List<EnvironmentalImpact> findAll();
    Optional<EnvironmentalImpact> findById(Long id);
    EnvironmentalImpact save(EnvironmentalImpact impact);
    void deleteById(Long id);
} 