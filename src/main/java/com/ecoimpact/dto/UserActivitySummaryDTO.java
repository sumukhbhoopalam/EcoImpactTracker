package com.ecoimpact.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserActivitySummaryDTO {
    private String userName;
    private Integer productsSearched;
    private Integer totalSearches;
    private LocalDateTime lastSearchDate;
} 