package com.ecoimpact.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProductImpactSummaryDTO {
    private Long productId;
    private String productName;
    private String brand;
    private String category;
    private Double carbonKg;
    private Double waterLiters;
    private Double wasteKg;
    private LocalDateTime recordedAt;
} 