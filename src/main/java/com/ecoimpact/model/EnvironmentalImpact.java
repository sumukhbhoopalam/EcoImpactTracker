package com.ecoimpact.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "environmental_impact")
public class EnvironmentalImpact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "impact_id")
    private Long impactId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "carbon_kg")
    private Double carbonKg;

    @Column(name = "water_liters")
    private Double waterLiters;

    @Column(name = "waste_kg")
    private Double wasteKg;

    @Column(name = "recorded_at")
    private LocalDateTime recordedAt;
} 