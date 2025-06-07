package com.ecoimpact.dto;

import lombok.Data;

@Data
public class EcoFriendlyProductDTO {
    private Long productId;
    private String productName;
    private String brand;
    private String category;
    private Double carbonKg;
    private Double waterLiters;
    private Double wasteKg;
    private Double totalImpactScore;
} 