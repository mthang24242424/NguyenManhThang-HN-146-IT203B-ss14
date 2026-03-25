create database Flash_Sale;

use Flash_Sale;

CREATE TABLE Users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) DEFAULT 'user',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);        
CREATE TABLE Products (  
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(100) NOT NULL,
    price DOUBLE NOT NULL,
    stock INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);  
CREATE TABLE Orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total double,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);              
CREATE TABLE Order_Details (  
    detail_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    product_id INT,
    quantity INT,
    price DOUBLE,
    FOREIGN KEY (order_id) REFERENCES Orders(order_id),
    FOREIGN KEY (product_id) REFERENCES Products(product_id)
);              


DELIMITER $$

CREATE procedure CalculateProductRevenue(p_id INT,out total double)

BEGIN
    SELECT SUM(quantity * price)
    INTO total
    FROM Order_Details
    WHERE product_id = p_id;
END $$

DELIMITER ;

select od.order_id,u.username,od.total,od.order_date from orders od join Users u on od.user_id = u.user_id;
