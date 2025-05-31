package com.ecoimpact.service.impl;

import com.ecoimpact.model.EnvironmentalImpact;
import com.ecoimpact.repository.EnvironmentalImpactRepository;
import com.ecoimpact.service.EnvironmentalImpactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnvironmentalImpactServiceImpl implements EnvironmentalImpactService {
    private final EnvironmentalImpactRepository environmentalImpactRepository;

    @Autowired
    public EnvironmentalImpactServiceImpl(EnvironmentalImpactRepository environmentalImpactRepository) {
        this.environmentalImpactRepository = environmentalImpactRepository;
    }

    @Override
    public List<EnvironmentalImpact> findAll() {
        return environmentalImpactRepository.findAll();
    }

    @Override
    public Optional<EnvironmentalImpact> findById(Long id) {
        return environmentalImpactRepository.findById(id);
    }

    @Override
    public EnvironmentalImpact save(EnvironmentalImpact impact) {
        return environmentalImpactRepository.save(impact);
    }

    @Override
    public void deleteById(Long id) {
        environmentalImpactRepository.deleteById(id);
    }
} 