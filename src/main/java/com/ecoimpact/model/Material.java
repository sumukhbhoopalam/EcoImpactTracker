package com.ecoimpact.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "materials")
@Getter
@Setter
public class Material extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "recyclability_percentage", precision = 5, scale = 2)
    private Double recyclabilityPercentage;
    
    @Column(name = "biodegradability_percentage", precision = 5, scale = 2)
    private Double biodegradabilityPercentage;
    
    @OneToMany(mappedBy = "material")
    private List<ProductMaterial> products = new ArrayList<>();
} 