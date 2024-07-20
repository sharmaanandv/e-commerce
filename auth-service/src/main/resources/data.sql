-- users table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    roles VARCHAR(100) NOT NULL
);

-- Populate User table
-- all passwords are admin
INSERT INTO users (username, password, roles) VALUES
('user', '$2a$10$yxEd4BkXTLISsiWZDSBioeZZozLFRDqqlarHxzPqSj7jBuvyfYG.u','ROLE_USER'),
('admin', '$2a$10$yxEd4BkXTLISsiWZDSBioeZZozLFRDqqlarHxzPqSj7jBuvyfYG.u','ROLE_ADMIN'),
('superuser', '$2a$10$yxEd4BkXTLISsiWZDSBioeZZozLFRDqqlarHxzPqSj7jBuvyfYG.u','ROLE_USER,ROLE_ADMIN');
