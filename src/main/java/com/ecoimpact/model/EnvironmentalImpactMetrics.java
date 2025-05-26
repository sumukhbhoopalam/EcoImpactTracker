package com.ecoimpact.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "environmental_impact_metrics")
@Getter
@Setter
public class EnvironmentalImpactMetrics extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @Column(name = "carbon_footprint_kg", precision = 10, scale = 3)
    private Double carbonFootprintKg;
    
    @Column(name = "water_usage_l", precision = 10, scale = 3)
    private Double waterUsageL;
    
    @Column(name = "waste_generated_kg", precision = 10, scale = 3)
    private Double wasteGeneratedKg;
    
    @Column(name = "recyclability_percentage", precision = 5, scale = 2)
    private Double recyclabilityPercentage;
    
    @Column(name = "biodegradability_percentage", precision = 5, scale = 2)
    private Double biodegradabilityPercentage;
} 