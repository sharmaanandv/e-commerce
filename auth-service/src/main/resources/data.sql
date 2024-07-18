-- users table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    roles VARCHAR(100) NOT NULL
);

-- Populate User table
-- password is admin
INSERT INTO users (username, password, roles) VALUES
('john', '$2a$10$yxEd4BkXTLISsiWZDSBioeZZozLFRDqqlarHxzPqSj7jBuvyfYG.u','user'),
('admin', '$2a$10$yxEd4BkXTLISsiWZDSBioeZZozLFRDqqlarHxzPqSj7jBuvyfYG.u','admin'),
('alice', '$2a$10$yxEd4BkXTLISsiWZDSBioeZZozLFRDqqlarHxzPqSj7jBuvyfYG.u','user,admin');
