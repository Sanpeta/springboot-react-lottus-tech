CREATE IF NOT EXISTS TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

CREATE IF NOT EXISTS TABLE orders (
    id SERIAL PRIMARY KEY,
    code VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    name_client VARCHAR(255) NOT NULL,
    quantity_total_itens INT NOT NULL,
    value_total DECIMAL(10, 2) NOT NULL
);

CREATE IF NOT EXISTS TABLE products_orders (
    product_id INT NOT NULL,
    order_id INT NOT NULL,
    PRIMARY KEY (product_id, order_id),
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (order_id) REFERENCES orders(id)
);