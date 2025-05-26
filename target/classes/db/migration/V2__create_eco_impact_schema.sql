-- Categories table for product categorization
CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    parent_category_id BIGINT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (parent_category_id) REFERENCES categories(id)
);

-- Brands table
CREATE TABLE brands (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    website VARCHAR(255),
    sustainability_rating DECIMAL(3,2),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Products table
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    brand_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    barcode VARCHAR(50),
    weight_kg DECIMAL(10,3),
    volume_l DECIMAL(10,3),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (brand_id) REFERENCES brands(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Environmental Impact Metrics table
CREATE TABLE environmental_impact_metrics (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    carbon_footprint_kg DECIMAL(10,3),
    water_usage_l DECIMAL(10,3),
    waste_generated_kg DECIMAL(10,3),
    recyclability_percentage DECIMAL(5,2),
    biodegradability_percentage DECIMAL(5,2),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Manufacturing Locations table
CREATE TABLE manufacturing_locations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    country VARCHAR(100) NOT NULL,
    city VARCHAR(100),
    address TEXT,
    latitude DECIMAL(10,8),
    longitude DECIMAL(11,8),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Product Manufacturing table (Many-to-Many relationship)
CREATE TABLE product_manufacturing (
    product_id BIGINT NOT NULL,
    location_id BIGINT NOT NULL,
    manufacturing_percentage DECIMAL(5,2) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    PRIMARY KEY (product_id, location_id),
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (location_id) REFERENCES manufacturing_locations(id)
);

-- Materials table
CREATE TABLE materials (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    recyclability_percentage DECIMAL(5,2),
    biodegradability_percentage DECIMAL(5,2),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Product Materials table (Many-to-Many relationship)
CREATE TABLE product_materials (
    product_id BIGINT NOT NULL,
    material_id BIGINT NOT NULL,
    percentage_used DECIMAL(5,2) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    PRIMARY KEY (product_id, material_id),
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (material_id) REFERENCES materials(id)
);

-- Certifications table
CREATE TABLE certifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    issuing_organization VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Product Certifications table (Many-to-Many relationship)
CREATE TABLE product_certifications (
    product_id BIGINT NOT NULL,
    certification_id BIGINT NOT NULL,
    certification_date DATE NOT NULL,
    expiry_date DATE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    PRIMARY KEY (product_id, certification_id),
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (certification_id) REFERENCES certifications(id)
);

-- User Product Reviews table
CREATE TABLE user_product_reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    rating DECIMAL(2,1) NOT NULL,
    review_text TEXT,
    sustainability_rating DECIMAL(2,1),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Add indexes for better query performance
CREATE INDEX idx_products_brand ON products(brand_id);
CREATE INDEX idx_products_category ON products(category_id);
CREATE INDEX idx_environmental_impact_product ON environmental_impact_metrics(product_id);
CREATE INDEX idx_product_manufacturing_product ON product_manufacturing(product_id);
CREATE INDEX idx_product_manufacturing_location ON product_manufacturing(location_id);
CREATE INDEX idx_product_materials_product ON product_materials(product_id);
CREATE INDEX idx_product_materials_material ON product_materials(material_id);
CREATE INDEX idx_product_certifications_product ON product_certifications(product_id);
CREATE INDEX idx_product_certifications_certification ON product_certifications(certification_id);
CREATE INDEX idx_user_reviews_user ON user_product_reviews(user_id);
CREATE INDEX idx_user_reviews_product ON user_product_reviews(product_id); 