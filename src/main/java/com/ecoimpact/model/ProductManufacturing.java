package com.ecoimpact.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_manufacturing")
@Getter
@Setter
public class ProductManufacturing extends BaseEntity {
    
    @EmbeddedId
    private ProductManufacturingId id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("locationId")
    @JoinColumn(name = "location_id")
    private ManufacturingLocation location;
    
    @Column(name = "manufacturing_percentage", nullable = false, precision = 5, scale = 2)
    private Double manufacturingPercentage;
}

@Embeddable
@Getter
@Setter
class ProductManufacturingId implements java.io.Serializable {
    
    @Column(name = "product_id")
    private Long productId;
    
    @Column(name = "location_id")
    private Long locationId;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductManufacturingId that = (ProductManufacturingId) o;
        return productId.equals(that.productId) && locationId.equals(that.locationId);
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(productId, locationId);
    }
} 