-- Transactions

-- Transaction 1: Add a new product with its environmental impact data
DELIMITER //
CREATE PROCEDURE add_product_with_impact(
    IN p_name VARCHAR(255),
    IN p_category VARCHAR(255),
    IN p_brand VARCHAR(255),
    IN p_carbon_kg DOUBLE,
    IN p_water_liters DOUBLE,
    IN p_waste_kg DOUBLE
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error occurred during product creation';
    END;

    START TRANSACTION;
    
    -- Insert product
    INSERT INTO product (name, category, brand, created_at)
    VALUES (p_name, p_category, p_brand, NOW());
    
    -- Get the last inserted product_id
    SET @last_product_id = LAST_INSERT_ID();
    
    -- Insert environmental impact
    INSERT INTO environmental_impact (product_id, carbon_kg, water_liters, waste_kg, recorded_at)
    VALUES (@last_product_id, p_carbon_kg, p_water_liters, p_waste_kg, NOW());
    
    COMMIT;
END //
DELIMITER ;

-- Transaction 2: Update product impact and record search history
DELIMITER //
CREATE PROCEDURE update_product_impact_and_search(
    IN p_product_id BIGINT,
    IN p_user_id BIGINT,
    IN p_carbon_kg DOUBLE,
    IN p_water_liters DOUBLE,
    IN p_waste_kg DOUBLE
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error occurred during update';
    END;

    START TRANSACTION;
    
    -- Update environmental impact
    UPDATE environmental_impact 
    SET carbon_kg = p_carbon_kg,
        water_liters = p_water_liters,
        waste_kg = p_waste_kg,
        recorded_at = NOW()
    WHERE product_id = p_product_id;
    
    -- Record search history
    INSERT INTO search_history (user_id, product_id, searched_at)
    VALUES (p_user_id, p_product_id, NOW());
    
    COMMIT;
END //
DELIMITER ;

-- Transaction 3: Get product impact summary with brand information
DELIMITER //
CREATE PROCEDURE get_product_impact_summary()
BEGIN
    SELECT 
        p.product_id,
        p.name as product_name,
        p.brand,
        p.category,
        ei.carbon_kg,
        ei.water_liters,
        ei.waste_kg,
        ei.recorded_at
    FROM product p
    JOIN environmental_impact ei ON p.product_id = ei.product_id
    ORDER BY ei.recorded_at DESC;
END //
DELIMITER ;

-- Transaction 4: Get user search history with product details
DELIMITER //
CREATE PROCEDURE get_user_search_history(IN p_user_id BIGINT)
BEGIN
    SELECT 
        sh.search_id,
        u.name as user_name,
        p.name as product_name,
        p.brand,
        p.category,
        sh.searched_at
    FROM search_history sh
    JOIN user u ON sh.user_id = u.user_id
    JOIN product p ON sh.product_id = p.product_id
    WHERE u.user_id = p_user_id
    ORDER BY sh.searched_at DESC;
END //
DELIMITER ;

-- Transaction 5: Get environmental impact statistics by category
DELIMITER //
CREATE PROCEDURE get_category_impact_stats()
BEGIN
    SELECT 
        p.category,
        AVG(ei.carbon_kg) as avg_carbon_kg,
        AVG(ei.water_liters) as avg_water_liters,
        AVG(ei.waste_kg) as avg_waste_kg,
        COUNT(p.product_id) as product_count
    FROM product p
    JOIN environmental_impact ei ON p.product_id = ei.product_id
    GROUP BY p.category
    ORDER BY avg_carbon_kg DESC;
END //
DELIMITER ;

-- Triggers

-- Trigger 1: Validate environmental impact data
DELIMITER //
CREATE TRIGGER validate_impact_data
BEFORE INSERT ON environmental_impact
FOR EACH ROW
BEGIN
    IF NEW.carbon_kg < 0 OR NEW.water_liters < 0 OR NEW.waste_kg < 0 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Environmental impact values cannot be negative';
    END IF;
END //
DELIMITER ;

-- Trigger 2: Update product timestamp on impact change
DELIMITER //
CREATE TRIGGER update_product_timestamp
AFTER UPDATE ON environmental_impact
FOR EACH ROW
BEGIN
    UPDATE product 
    SET created_at = NOW()
    WHERE product_id = NEW.product_id;
END //
DELIMITER ;

-- Trigger 3: Prevent duplicate search history entries
DELIMITER //
CREATE TRIGGER prevent_duplicate_searches
BEFORE INSERT ON search_history
FOR EACH ROW
BEGIN
    IF EXISTS (
        SELECT 1 FROM search_history 
        WHERE user_id = NEW.user_id 
        AND product_id = NEW.product_id 
        AND searched_at > DATE_SUB(NOW(), INTERVAL 1 HOUR)
    ) THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Duplicate search detected within 1 hour';
    END IF;
END //
DELIMITER ;

-- Stored Procedures

-- Procedure 1: Calculate total environmental impact for a brand
DELIMITER //
CREATE PROCEDURE calculate_brand_impact(IN p_brand VARCHAR(255))
BEGIN
    SELECT 
        p.brand,
        SUM(ei.carbon_kg) as total_carbon_kg,
        SUM(ei.water_liters) as total_water_liters,
        SUM(ei.waste_kg) as total_waste_kg,
        COUNT(p.product_id) as product_count
    FROM product p
    JOIN environmental_impact ei ON p.product_id = ei.product_id
    WHERE p.brand = p_brand
    GROUP BY p.brand;
END //
DELIMITER ;

-- Procedure 2: Get user activity summary
DELIMITER //
CREATE PROCEDURE get_user_activity_summary(IN p_user_id BIGINT)
BEGIN
    SELECT 
        u.name as user_name,
        COUNT(DISTINCT sh.product_id) as products_searched,
        COUNT(sh.search_id) as total_searches,
        MAX(sh.searched_at) as last_search_date
    FROM user u
    LEFT JOIN search_history sh ON u.user_id = sh.user_id
    WHERE u.user_id = p_user_id
    GROUP BY u.user_id, u.name;
END //
DELIMITER ;

-- Procedure 3: Get top environmentally friendly products
DELIMITER //
CREATE PROCEDURE get_top_eco_friendly_products(IN p_limit INT)
BEGIN
    SELECT 
        p.product_id,
        p.name as product_name,
        p.brand,
        p.category,
        ei.carbon_kg,
        ei.water_liters,
        ei.waste_kg,
        (ei.carbon_kg + ei.water_liters/1000 + ei.waste_kg) as total_impact_score
    FROM product p
    JOIN environmental_impact ei ON p.product_id = ei.product_id
    ORDER BY total_impact_score ASC
    LIMIT p_limit;
END //
DELIMITER ; 