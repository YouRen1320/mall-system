-- 创建数据库
CREATE DATABASE IF NOT EXISTS mall_system
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_0900_ai_ci;

USE mall_system;


-- 商品分类表
CREATE TABLE category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    status TINYINT NOT NULL DEFAULT 1,
    sort INT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP
);


-- 商品表
CREATE TABLE product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id BIGINT NOT NULL,
    name VARCHAR(200) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    description TEXT,
    image_url VARCHAR(500),
    status TINYINT NOT NULL DEFAULT 1,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    INDEX idx_category_id (category_id),

    CONSTRAINT fk_product_category
        FOREIGN KEY (category_id)
        REFERENCES category(id)
);


-- 初始化商品分类
INSERT INTO category (name, sort)
VALUES
('手机', 1),
('电脑', 2),
('食品', 3);


-- 初始化商品
INSERT INTO product
(category_id, name, price, stock, description)
VALUES
(1, 'iPhone 17', 5999.00, 100, 'Apple 手机'),
(1, '小米手机', 2999.00, 200, '小米智能手机'),
(2, 'MacBook Air', 7999.00, 50, 'Apple 笔记本电脑'),
(3, '苹果', 6.99, 500, '新鲜水果');