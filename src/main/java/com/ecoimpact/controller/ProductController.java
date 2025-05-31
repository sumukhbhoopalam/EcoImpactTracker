package com.ecoimpact.controller;

import com.ecoimpact.model.Product;
import com.ecoimpact.model.EnvironmentalImpact;
import com.ecoimpact.service.ProductService;
import com.ecoimpact.service.EnvironmentalImpactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final EnvironmentalImpactService environmentalImpactService;

    @Autowired
    public ProductController(ProductService productService, EnvironmentalImpactService environmentalImpactService) {
        this.productService = productService;
        this.environmentalImpactService = environmentalImpactService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.findById(id)
                .map(existing -> {
                    product.setProductId(id);
                    return ResponseEntity.ok(productService.save(product));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(
        summary = "Search for a product by name and get its environmental impact",
        description = "Returns the product, its environmental impact, and the calculated ecoImpact for the given product name."
    )
    @ApiResponse(responseCode = "200", description = "Product and environmental impact found")
    @ApiResponse(responseCode = "404", description = "Product not found")
    public ResponseEntity<?> searchProductWithImpact(@RequestParam String name) {
        // Find product by name (case-insensitive, first match)
        return productService.findAll().stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .map(product -> {
                    // Find environmental impact for this product
                    EnvironmentalImpact impact = environmentalImpactService.findAll().stream()
                        .filter(ei -> ei.getProduct().getProductId().equals(product.getProductId()))
                        .findFirst().orElse(null);
                    Double ecoImpact = null;
                    if (impact != null) {
                        Double C = impact.getCarbonKg() != null ? impact.getCarbonKg() : 0.0;
                        Double W = impact.getWaterLiters() != null ? impact.getWaterLiters() : 0.0;
                        Double S = impact.getWasteKg() != null ? impact.getWasteKg() : 0.0;
                        ecoImpact = (1.0/3.0) * (C/20.0) + (1.0/3.0) * (W/0.20) + (1.0/3.0) * (S/0.10);
                    }
                    return ResponseEntity.ok(new ProductWithImpactResponse(product, impact, ecoImpact));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DTO for response
    static class ProductWithImpactResponse {
        public final Product product;
        public final EnvironmentalImpact environmentalImpact;
        public final Double ecoImpact;
        public ProductWithImpactResponse(Product product, EnvironmentalImpact environmentalImpact, Double ecoImpact) {
            this.product = product;
            this.environmentalImpact = environmentalImpact;
            this.ecoImpact = ecoImpact;
        }
    }
} 