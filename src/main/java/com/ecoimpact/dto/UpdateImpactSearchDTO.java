package com.ecoimpact.dto;

import lombok.Data;

@Data
public class UpdateImpactSearchDTO {
    private Long productId;
    private Long userId;
    private Double carbonKg;
    private Double waterLiters;
    private Double wasteKg;
} 