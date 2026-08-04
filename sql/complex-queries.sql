-- ==========================================
-- COMPLEX SQL QUERIES
-- ==========================================

-- 1. INNER JOIN
SELECT
    c.name AS customer_name,
    p.name AS product_name,
    o.quantity
FROM orders o
INNER JOIN customers c
    ON c.id = o.customer_id
INNER JOIN products p
    ON p.id = o.product_id;


-- 2. LEFT OUTER JOIN
SELECT
    c.name AS customer_name,
    o.id AS order_id
FROM customers c
LEFT OUTER JOIN orders o
    ON c.id = o.customer_id;


-- 3. GROUP BY + COUNT + SUM
SELECT
    c.name,
    COUNT(o.id) AS order_count,
    COALESCE(SUM(p.price * o.quantity),0) AS total_spent
FROM customers c
LEFT JOIN orders o
    ON c.id = o.customer_id
LEFT JOIN products p
    ON p.id = o.product_id
GROUP BY c.id, c.name
ORDER BY total_spent DESC;


-- 4. WITH (CTE)
WITH customer_totals AS (
    SELECT
        c.id,
        c.name,
        SUM(p.price * o.quantity) AS total_spent
    FROM customers c
    JOIN orders o
        ON c.id = o.customer_id
    JOIN products p
        ON p.id = o.product_id
    GROUP BY c.id, c.name
)
SELECT *
FROM customer_totals
WHERE total_spent > 10000;


-- 5. UNION
SELECT name
FROM customers

UNION

SELECT name
FROM products;


-- 6. UNION ALL
SELECT city
FROM customers

UNION ALL

SELECT name
FROM products;


-- 7. EXCEPT (MINUS equivalent in PostgreSQL)
SELECT id, name
FROM customers

EXCEPT

SELECT c.id, c.name
FROM customers c
JOIN orders o
    ON c.id = o.customer_id;


-- 8. DISTINCT
SELECT DISTINCT city
FROM customers;


-- 9. COALESCE
SELECT
    c.name,
    COALESCE(SUM(p.price * o.quantity),0) AS total_spent
FROM customers c
LEFT JOIN orders o
    ON c.id = o.customer_id
LEFT JOIN products p
    ON p.id = o.product_id
GROUP BY c.id, c.name;


-- 10. COUNT
SELECT COUNT(*) AS total_orders
FROM orders;


-- 11. SUM
SELECT SUM(quantity) AS total_products_sold
FROM orders;


-- 12. WHERE EXISTS
SELECT
    c.id,
    c.name
FROM customers c
WHERE EXISTS (
    SELECT 1
    FROM orders o
    WHERE o.customer_id = c.id
);


-- 13. WHERE NOT EXISTS
SELECT
    c.id,
    c.name
FROM customers c
WHERE NOT EXISTS (
    SELECT 1
    FROM orders o
    WHERE o.customer_id = c.id
);