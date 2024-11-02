INSERT INTO orders (code, date, client_name, total_items, total_value) 
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
    client_name = $4,
    total_items = $5,
    total_value = $6,
WHERE id = $1
RETURNING *;

DELETE FROM orders
WHERE id = $1;