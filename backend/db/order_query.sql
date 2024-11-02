INSERT INTO orders (code, date, name_client, quantity_total_itens, value_total, product_id) 
VALUES ($1, $2, $3, $4, $5, $6)
RETURNING *;

SELECT * FROM orders
WHERE id = $1 
LIMIT 1
FOR NO KEY UPDATE;

UPDATE orders
SET
    code = $2,
    date = $3,
    name_client = $4,
    quantity_total_itens = $5,
    value_total = $6,
    product_id = $7
WHERE id = $1
RETURNING *;

DELETE FROM orders
WHERE id = $1;