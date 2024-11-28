TRUNCATE TABLE customer_data RESTART IDENTITY CASCADE;

INSERT INTO customer_data (customer_id, customer_name) VALUES (1, 'Harshit gupta');

INSERT INTO transaction_data (id, customer_id, date_of_transaction, amount_of_transaction) VALUES
(1, 1, '2024-08-01', 120.00),
(2, 1, '2024-09-01', 75.00),
(3, 1, '2024-10-01', 150.00),
(4, 1, '2024-08-15', 95.00),
(5, 1, '2024-09-20', 130.00);
