package com.ecoimpact.dto;

import lombok.Data;

@Data
public class ProductWithImpactDTO {
    private String name;
    private String category;
    private String brand;
    private Double carbonKg;
    private Double waterLiters;
    private Double wasteKg;
    private Double ecoImpactScore;
} 