package com.ecoimpact.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "certifications")
@Getter
@Setter
public class Certification extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "issuing_organization", length = 255)
    private String issuingOrganization;
    
    @OneToMany(mappedBy = "certification")
    private List<ProductCertification> products = new ArrayList<>();
} 