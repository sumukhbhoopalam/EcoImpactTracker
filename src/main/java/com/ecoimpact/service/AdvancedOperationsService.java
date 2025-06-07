package com.ecoimpact.service;

import com.ecoimpact.dto.*;
import java.util.List;

public interface AdvancedOperationsService {
    // Transaction operations
    ProductWithImpactDTO addProductWithImpact(ProductWithImpactDTO productWithImpact);
    void updateProductImpactAndSearch(UpdateImpactSearchDTO updateData);
    List<ProductImpactSummaryDTO> getProductImpactSummary();
    List<UserSearchHistoryDTO> getUserSearchHistory(Long userId);
    List<CategoryImpactStatsDTO> getCategoryImpactStats();
    
    // Brand impact operations
    BrandImpactDTO calculateBrandImpact(String brand);
    
    // User activity operations
    UserActivitySummaryDTO getUserActivitySummary(Long userId);
    
    // Eco-friendly products
    List<EcoFriendlyProductDTO> getTopEcoFriendlyProducts(int limit);
} 