package com.ecoimpact.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_materials")
@Getter
@Setter
public class ProductMaterial extends BaseEntity {
    
    @EmbeddedId
    private ProductMaterialId id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("materialId")
    @JoinColumn(name = "material_id")
    private Material material;
    
    @Column(name = "percentage_used", nullable = false, precision = 5, scale = 2)
    private Double percentageUsed;
}

@Embeddable
@Getter
@Setter
class ProductMaterialId implements java.io.Serializable {
    
    @Column(name = "product_id")
    private Long productId;
    
    @Column(name = "material_id")
    private Long materialId;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductMaterialId that = (ProductMaterialId) o;
        return productId.equals(that.productId) && materialId.equals(that.materialId);
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(productId, materialId);
    }
} 