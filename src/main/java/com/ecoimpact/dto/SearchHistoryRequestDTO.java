package com.ecoimpact.dto;

import lombok.Data;

@Data
public class SearchHistoryRequestDTO {
    private String searchQuery;
    private String timestamp;
} 