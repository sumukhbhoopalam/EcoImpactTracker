package com.ecoimpact.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "manufacturing_locations")
@Getter
@Setter
public class ManufacturingLocation extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 255)
    private String name;
    
    @Column(nullable = false, length = 100)
    private String country;
    
    @Column(length = 100)
    private String city;
    
    @Column(columnDefinition = "TEXT")
    private String address;
    
    @Column(precision = 10, scale = 8)
    private Double latitude;
    
    @Column(precision = 11, scale = 8)
    private Double longitude;
    
    @OneToMany(mappedBy = "location")
    private List<ProductManufacturing> products = new ArrayList<>();
} 