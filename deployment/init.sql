CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(200) NOT NULL,
    price NUMERIC(10, 2) NOT NULL CHECK (price > 0),
    quantity INTEGER NOT NULL CHECK (quantity >= 0)
);

INSERT INTO products (name, description, price, quantity) VALUES
('Refrigerator', 'A large refrigerator with a freezer compartment', 500.00, 50),
('Washing Machine', 'A high-efficiency washing machine', 300.00, 30),
('Microwave Oven', 'A compact microwave oven', 100.00, 100),
('Dishwasher', 'A built-in dishwasher with multiple settings', 400.00, 20),
('Air Conditioner', 'A window air conditioner unit', 250.00, 40);	