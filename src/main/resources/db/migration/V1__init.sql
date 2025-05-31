CREATE TABLE user (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    created_at DATETIME
);

CREATE TABLE product (
    product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    brand VARCHAR(255) NOT NULL,
    created_at DATETIME
);

CREATE TABLE environmental_impact (
    impact_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    carbon_kg DOUBLE,
    water_liters DOUBLE,
    waste_kg DOUBLE,
    recorded_at DATETIME,
    CONSTRAINT fk_env_product FOREIGN KEY (product_id) REFERENCES product(product_id)
);

CREATE TABLE search_history (
    search_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    searched_at DATETIME,
    CONSTRAINT fk_search_user FOREIGN KEY (user_id) REFERENCES user(user_id),
    CONSTRAINT fk_search_product FOREIGN KEY (product_id) REFERENCES product(product_id)
); 