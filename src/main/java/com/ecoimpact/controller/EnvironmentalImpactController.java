package com.ecoimpact.controller;

import com.ecoimpact.model.EnvironmentalImpact;
import com.ecoimpact.service.EnvironmentalImpactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/environmental-impacts")
public class EnvironmentalImpactController {
    private final EnvironmentalImpactService environmentalImpactService;

    @Autowired
    public EnvironmentalImpactController(EnvironmentalImpactService environmentalImpactService) {
        this.environmentalImpactService = environmentalImpactService;
    }

    @GetMapping
    public List<EnvironmentalImpact> getAllEnvironmentalImpacts() {
        return environmentalImpactService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvironmentalImpact> getEnvironmentalImpactById(@PathVariable Long id) {
        return environmentalImpactService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EnvironmentalImpact createEnvironmentalImpact(@RequestBody EnvironmentalImpact impact) {
        return environmentalImpactService.save(impact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnvironmentalImpact> updateEnvironmentalImpact(@PathVariable Long id, @RequestBody EnvironmentalImpact impact) {
        return environmentalImpactService.findById(id)
                .map(existing -> {
                    impact.setImpactId(id);
                    return ResponseEntity.ok(environmentalImpactService.save(impact));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnvironmentalImpact(@PathVariable Long id) {
        environmentalImpactService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
} 