CREATE DATABASE CustomerServiceManagement;

USE CustomerServiceManagement;

CREATE TABLE Customer (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(15) NOT NULL
);

CREATE TABLE Inquiry (
    inquiry_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    inquiry_date DATE,
    description TEXT,
    status VARCHAR(50),
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
);

CREATE TABLE Complaint (
    complaint_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    complaint_date DATE,
    description TEXT,
    status VARCHAR(50),
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
);

CREATE TABLE Resolution (
    resolution_id INT AUTO_INCREMENT PRIMARY KEY,
    inquiry_id INT,
    complaint_id INT,
    resolution_date DATE,
    details TEXT,
    FOREIGN KEY (inquiry_id) REFERENCES Inquiry(inquiry_id),
    FOREIGN KEY (complaint_id) REFERENCES Complaint(complaint_id) 
);


select * from Resolution;

INSERT INTO Customer (name, email, phone) VALUES ('John Doe', 'john.doe@example.com', '123-456-7890');
INSERT INTO Customer (name, email, phone) VALUES ('Jane Smith', 'jane.smith@example.com', '123-456-7891');
INSERT INTO Customer (name, email, phone) VALUES ('Alice Johnson', 'alice.johnson@example.com', '123-456-7892');
INSERT INTO Customer (name, email, phone) VALUES ('Bob Brown', 'bob.brown@example.com', '123-456-7893');
INSERT INTO Customer (name, email, phone) VALUES ('Charlie Davis', 'charlie.davis@example.com', '123-456-7894');
INSERT INTO Customer (name, email, phone) VALUES ('Diana Evans', 'diana.evans@example.com', '123-456-7895');
INSERT INTO Customer (name, email, phone) VALUES ('Frank Green', 'frank.green@example.com', '123-456-7896');
INSERT INTO Customer (name, email, phone) VALUES ('Grace Harris', 'grace.harris@example.com', '123-456-7897');
INSERT INTO Customer (name, email, phone) VALUES ('Henry Jackson', 'henry.jackson@example.com', '123-456-7898');
INSERT INTO Customer (name, email, phone) VALUES ('Ivy King', 'ivy.king@example.com', '123-456-7899');

select * from Complaint;

