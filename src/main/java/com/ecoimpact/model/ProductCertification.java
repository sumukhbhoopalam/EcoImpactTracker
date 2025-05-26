package com.ecoimpact.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "product_certifications")
@Getter
@Setter
public class ProductCertification extends BaseEntity {
    
    @EmbeddedId
    private ProductCertificationId id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("certificationId")
    @JoinColumn(name = "certification_id")
    private Certification certification;
    
    @Column(name = "certification_date", nullable = false)
    private LocalDate certificationDate;
    
    @Column(name = "expiry_date")
    private LocalDate expiryDate;
}

@Embeddable
@Getter
@Setter
class ProductCertificationId implements java.io.Serializable {
    
    @Column(name = "product_id")
    private Long productId;
    
    @Column(name = "certification_id")
    private Long certificationId;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCertificationId that = (ProductCertificationId) o;
        return productId.equals(that.productId) && certificationId.equals(that.certificationId);
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(productId, certificationId);
    }
} 