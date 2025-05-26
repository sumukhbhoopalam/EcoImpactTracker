package com.ecoimpact.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 255)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    
    @Column(length = 50)
    private String barcode;
    
    @Column(name = "weight_kg", precision = 10, scale = 3)
    private Double weightKg;
    
    @Column(name = "volume_l", precision = 10, scale = 3)
    private Double volumeL;
    
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private EnvironmentalImpactMetrics environmentalImpactMetrics;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductManufacturing> manufacturingLocations = new ArrayList<>();
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductMaterial> materials = new ArrayList<>();
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductCertification> certifications = new ArrayList<>();
    
    @OneToMany(mappedBy = "product")
    private List<UserProductReview> reviews = new ArrayList<>();
} 