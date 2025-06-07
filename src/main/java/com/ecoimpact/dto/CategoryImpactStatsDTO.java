package com.ecoimpact.dto;

import lombok.Data;

@Data
public class CategoryImpactStatsDTO {
    private String category;
    private Double avgCarbonKg;
    private Double avgWaterLiters;
    private Double avgWasteKg;
    private Integer productCount;
} 