package com.ecoimpact.service.impl;

import com.ecoimpact.dto.*;
import com.ecoimpact.service.AdvancedOperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdvancedOperationsServiceImpl implements AdvancedOperationsService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AdvancedOperationsServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public ProductWithImpactDTO addProductWithImpact(ProductWithImpactDTO productWithImpact) {
        jdbcTemplate.update(
            "CALL add_product_with_impact(?, ?, ?, ?, ?, ?)",
            productWithImpact.getName(),
            productWithImpact.getCategory(),
            productWithImpact.getBrand(),
            productWithImpact.getCarbonKg(),
            productWithImpact.getWaterLiters(),
            productWithImpact.getWasteKg()
        );
        return productWithImpact;
    }

    @Override
    @Transactional
    public void updateProductImpactAndSearch(UpdateImpactSearchDTO updateData) {
        jdbcTemplate.update(
            "CALL update_product_impact_and_search(?, ?, ?, ?, ?)",
            updateData.getProductId(),
            updateData.getUserId(),
            updateData.getCarbonKg(),
            updateData.getWaterLiters(),
            updateData.getWasteKg()
        );
    }

    @Override
    public List<ProductImpactSummaryDTO> getProductImpactSummary() {
        return jdbcTemplate.query(
            "CALL get_product_impact_summary()",
            (rs, rowNum) -> {
                ProductImpactSummaryDTO dto = new ProductImpactSummaryDTO();
                dto.setProductId(rs.getLong("product_id"));
                dto.setProductName(rs.getString("product_name"));
                dto.setBrand(rs.getString("brand"));
                dto.setCategory(rs.getString("category"));
                dto.setCarbonKg(rs.getDouble("carbon_kg"));
                dto.setWaterLiters(rs.getDouble("water_liters"));
                dto.setWasteKg(rs.getDouble("waste_kg"));
                dto.setRecordedAt(rs.getTimestamp("recorded_at").toLocalDateTime());
                return dto;
            }
        );
    }

    @Override
    public List<UserSearchHistoryDTO> getUserSearchHistory(Long userId) {
        return jdbcTemplate.query(
            "CALL get_user_search_history(?)",
            (rs, rowNum) -> {
                UserSearchHistoryDTO dto = new UserSearchHistoryDTO();
                dto.setSearchId(rs.getLong("search_id"));
                dto.setUserName(rs.getString("user_name"));
                dto.setProductName(rs.getString("product_name"));
                dto.setBrand(rs.getString("brand"));
                dto.setCategory(rs.getString("category"));
                dto.setSearchedAt(rs.getTimestamp("searched_at").toLocalDateTime());
                return dto;
            },
            userId
        );
    }

    @Override
    public List<CategoryImpactStatsDTO> getCategoryImpactStats() {
        return jdbcTemplate.query(
            "CALL get_category_impact_stats()",
            (rs, rowNum) -> {
                CategoryImpactStatsDTO dto = new CategoryImpactStatsDTO();
                dto.setCategory(rs.getString("category"));
                dto.setAvgCarbonKg(rs.getDouble("avg_carbon_kg"));
                dto.setAvgWaterLiters(rs.getDouble("avg_water_liters"));
                dto.setAvgWasteKg(rs.getDouble("avg_waste_kg"));
                dto.setProductCount(rs.getInt("product_count"));
                return dto;
            }
        );
    }

    @Override
    public BrandImpactDTO calculateBrandImpact(String brand) {
        return jdbcTemplate.queryForObject(
            "CALL calculate_brand_impact(?)",
            (rs, rowNum) -> {
                BrandImpactDTO dto = new BrandImpactDTO();
                dto.setBrand(rs.getString("brand"));
                dto.setTotalCarbonKg(rs.getDouble("total_carbon_kg"));
                dto.setTotalWaterLiters(rs.getDouble("total_water_liters"));
                dto.setTotalWasteKg(rs.getDouble("total_waste_kg"));
                dto.setProductCount(rs.getInt("product_count"));
                return dto;
            },
            brand
        );
    }

    @Override
    public UserActivitySummaryDTO getUserActivitySummary(Long userId) {
        return jdbcTemplate.queryForObject(
            "CALL get_user_activity_summary(?)",
            (rs, rowNum) -> {
                UserActivitySummaryDTO dto = new UserActivitySummaryDTO();
                dto.setUserName(rs.getString("user_name"));
                dto.setProductsSearched(rs.getInt("products_searched"));
                dto.setTotalSearches(rs.getInt("total_searches"));
                dto.setLastSearchDate(rs.getTimestamp("last_search_date").toLocalDateTime());
                return dto;
            },
            userId
        );
    }

    @Override
    public List<EcoFriendlyProductDTO> getTopEcoFriendlyProducts(int limit) {
        return jdbcTemplate.query(
            "CALL get_top_eco_friendly_products(?)",
            (rs, rowNum) -> {
                EcoFriendlyProductDTO dto = new EcoFriendlyProductDTO();
                dto.setProductId(rs.getLong("product_id"));
                dto.setProductName(rs.getString("product_name"));
                dto.setBrand(rs.getString("brand"));
                dto.setCategory(rs.getString("category"));
                dto.setCarbonKg(rs.getDouble("carbon_kg"));
                dto.setWaterLiters(rs.getDouble("water_liters"));
                dto.setWasteKg(rs.getDouble("waste_kg"));
                
                // Calculate eco impact score using a more sophisticated formula
                // Normalize each impact factor to a 0-1 scale using sigmoid function
                double carbonScore = 1 / (1 + Math.exp(dto.getCarbonKg() - 5)); // 5 kg CO2 as midpoint
                double waterScore = 1 / (1 + Math.exp(dto.getWaterLiters() * 0.001 - 2)); // 2000L as midpoint
                double wasteScore = 1 / (1 + Math.exp(dto.getWasteKg() - 3)); // 3 kg waste as midpoint
                
                // Weighted average of normalized scores
                double weightedScore = (carbonScore * 0.4 + waterScore * 0.3 + wasteScore * 0.3);
                
                // Scale to 0-10 range
                double finalScore = weightedScore * 10;
                dto.setTotalImpactScore(Math.round(finalScore * 10.0) / 10.0); // Round to 1 decimal place
                
                return dto;
            },
            limit
        );
    }
} 