-- Create Product table
CREATE TABLE product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL,
    version INT NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE
);

-- Populate Product table
INSERT INTO product (name, price, stock, version) VALUES
('Laptop', 999.99, 10,1),
('Smartphone', 699.99, 25,1),
('Tablet', 399.99, 15,1);
