-- Create Order table
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    product_id BIGINT,
    stock INT NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted BOOLEAN DEFAULT FALSE
);

-- Populate Order table
INSERT INTO orders (user_id, product_id, stock, total_amount) VALUES
(1, 1, 1, 999.99),
(2, 2, 2, 1399.98),
(3, 3, 1, 399.99);
