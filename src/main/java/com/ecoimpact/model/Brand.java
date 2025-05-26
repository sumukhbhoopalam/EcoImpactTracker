package com.ecoimpact.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "brands")
@Getter
@Setter
public class Brand extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(length = 255)
    private String website;
    
    @Column(name = "sustainability_rating", precision = 3, scale = 2)
    private Double sustainabilityRating;
    
    @OneToMany(mappedBy = "brand")
    private List<Product> products = new ArrayList<>();
} 