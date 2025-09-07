ALTER TABLE orders ADD COLUMN qr_url VARCHAR(255);

UPDATE orders o
SET qr_url = r.qr_url
FROM receipts r
WHERE r.order_id = o.order_id AND r.qr_url IS NOT NULL;

ALTER TABLE receipts DROP COLUMN qr_url;
