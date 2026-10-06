-- ============================================================
-- Product data fix script
-- Run once against ecommDB.
--
-- What this script does:
--   1. Removes exact duplicate product names within the same category.
--   2. Inserts 6 new Trending products (category = 'Trending').
--
-- What this script does NOT do:
--   - Does not drop or recreate the product table.
--   - Does not modify any other table (user, orders, order_item).
--   - Does not change any existing unique product.
--   - Does not touch backend code, frontend code, or schema.
-- ============================================================

USE ecommDB;

-- ─────────────────────────────────────────────────────────────
-- STEP 1: Remove exact duplicate product names in Clothing
-- ─────────────────────────────────────────────────────────────
-- id=25 'Classic Cotton T-Shirt' (Clothing) is a duplicate of id=1.
-- Keep id=1 (the original, lower id). Remove id=25.
DELETE FROM product WHERE id = 25;

-- ─────────────────────────────────────────────────────────────
-- STEP 2: Remove exact duplicate product names in Electronics
-- ─────────────────────────────────────────────────────────────
-- id=29 'Wireless Headphones' (Electronics) is a duplicate of id=15.
-- Keep id=15 (the original, lower id). Remove id=29.
DELETE FROM product WHERE id = 29;

-- ─────────────────────────────────────────────────────────────
-- STEP 3: Insert 6 new Trending products
--
-- Requested list:  Smartwatch, Wireless Headphones, Smartphone,
--                  Sneakers, Backpack, Sunglasses
--
-- Smartwatch       → already exists in Electronics (id=28). Skipped.
-- Wireless Headphones → already exists in Electronics (id=15). Skipped.
-- Smartphone       → already exists in Electronics (id=30). Skipped.
-- Sneakers         → does not exist. Added.
-- Backpack         → does not exist. Added.
-- Sunglasses       → does not exist. Added.
--
-- 3 substitutes for the skipped items (all verified absent):
--   Running Cap, Leather Wallet, Casual Watch
-- ─────────────────────────────────────────────────────────────
INSERT INTO product (name, description, price, image_url, category) VALUES
(
    'Sneakers',
    'Lightweight everyday sneakers with cushioned sole and breathable mesh upper. Ideal for casual walks and light workouts.',
    1299.00,
    'https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=600&q=80',
    'Trending'
),
(
    'Backpack',
    'Durable 30L travel backpack with padded laptop compartment, multiple pockets, and ergonomic shoulder straps.',
    1799.00,
    'https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=600&q=80',
    'Trending'
),
(
    'Sunglasses',
    'UV400 polarised sunglasses with lightweight frame. Protects against UVA and UVB rays. Unisex design.',
    899.00,
    'https://images.unsplash.com/photo-1511499767150-a48a237f0083?w=600&q=80',
    'Trending'
),
(
    'Running Cap',
    'Moisture-wicking athletic cap with adjustable strap and breathable mesh panels. Perfect for outdoor runs.',
    499.00,
    'https://images.unsplash.com/photo-1588850561407-ed78c282e89b?w=600&q=80',
    'Trending'
),
(
    'Leather Wallet',
    'Slim genuine leather bifold wallet with 6 card slots, a cash compartment, and RFID blocking protection.',
    749.00,
    'https://images.unsplash.com/photo-1627123424574-724758594e93?w=600&q=80',
    'Trending'
),
(
    'Casual Watch',
    'Minimalist analog watch with stainless steel case, leather strap, and scratch-resistant mineral glass.',
    2499.00,
    'https://images.unsplash.com/photo-1524592094714-0f0654e20314?w=600&q=80',
    'Trending'
);
