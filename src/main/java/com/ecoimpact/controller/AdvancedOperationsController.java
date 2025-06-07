package com.ecoimpact.controller;

import com.ecoimpact.dto.*;
import com.ecoimpact.service.AdvancedOperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AdvancedOperationsController {

    private final AdvancedOperationsService advancedOperationsService;

    @Autowired
    public AdvancedOperationsController(AdvancedOperationsService advancedOperationsService) {
        this.advancedOperationsService = advancedOperationsService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductImpactSummaryDTO>> getAllProducts() {
        return ResponseEntity.ok(advancedOperationsService.getProductImpactSummary());
    }

    @GetMapping("/products/search")
    public ResponseEntity<ProductWithImpactDTO> searchProduct(@RequestParam String name) {
        // Find product by name and return with impact
        List<ProductImpactSummaryDTO> products = advancedOperationsService.getProductImpactSummary();
        return products.stream()
                .filter(p -> p.getProductName().equalsIgnoreCase(name))
                .findFirst()
                .map(p -> {
                    ProductWithImpactDTO dto = new ProductWithImpactDTO();
                    dto.setName(p.getProductName());
                    dto.setBrand(p.getBrand());
                    dto.setCategory(p.getCategory());
                    dto.setCarbonKg(p.getCarbonKg());
                    dto.setWaterLiters(p.getWaterLiters());
                    dto.setWasteKg(p.getWasteKg());
                    
                    // Calculate eco impact score
                    double score = 100 - ((p.getCarbonKg() * 0.4 + p.getWaterLiters() * 0.0001 + p.getWasteKg() * 0.6) * 10);
                    dto.setEcoImpactScore(Math.max(0, Math.min(100, score)));
                    
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/environmental-impacts/{productId}")
    public ResponseEntity<EnvironmentalImpactDTO> getEnvironmentalImpact(@PathVariable Long productId) {
        return advancedOperationsService.getProductImpactSummary().stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst()
                .map(p -> {
                    EnvironmentalImpactDTO dto = new EnvironmentalImpactDTO();
                    dto.setCarbonKg(p.getCarbonKg());
                    dto.setWaterLiters(p.getWaterLiters());
                    dto.setWasteKg(p.getWasteKg());
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/categories/impact-stats")
    public ResponseEntity<List<CategoryImpactStatsDTO>> getCategoryImpactStats() {
        return ResponseEntity.ok(advancedOperationsService.getCategoryImpactStats());
    }

    @GetMapping("/brands/{brand}/impact")
    public ResponseEntity<BrandImpactDTO> calculateBrandImpact(@PathVariable String brand) {
        return ResponseEntity.ok(advancedOperationsService.calculateBrandImpact(brand));
    }

    @GetMapping("/products/eco-friendly")
    public ResponseEntity<List<EcoFriendlyProductDTO>> getTopEcoFriendlyProducts(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(advancedOperationsService.getTopEcoFriendlyProducts(limit));
    }
} 