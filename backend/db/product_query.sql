INSERT INTO products (name, code, description, price) 
VALUES ($1, $2, $3, $4)
RETURNING *;

SELECT * FROM products
WHERE id = $1 
LIMIT 1
FOR NO KEY UPDATE;

UPDATE products
SET
    name = $2,
    code = $3,
    description = $4,
    price = $5
WHERE id = $1
RETURNING *;

DELETE FROM products
WHERE id = $1;