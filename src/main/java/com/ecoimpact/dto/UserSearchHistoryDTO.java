package com.ecoimpact.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserSearchHistoryDTO {
    private Long searchId;
    private String userName;
    private String productName;
    private String brand;
    private String category;
    private LocalDateTime searchedAt;
} 