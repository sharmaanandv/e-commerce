-- Create Order table
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    product_id BIGINT,
    quantity INT NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted BOOLEAN DEFAULT FALSE
);

-- Populate Order table
INSERT INTO orders (user_id, product_id, quantity, total_amount) VALUES
(1, 1, 10, 999.99),
(1, 2, 20, 1399.98),
(2, 3, 10, 399.99),
(2, 3, 10, 399.99),
(2, 3, 10, 399.99);
