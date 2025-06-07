package com.ecoimpact.dto;

import lombok.Data;

@Data
public class BrandImpactDTO {
    private String brand;
    private Double totalCarbonKg;
    private Double totalWaterLiters;
    private Double totalWasteKg;
    private Integer productCount;
} 