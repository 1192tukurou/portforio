-- 商品のサンプルデータ
-- 既にデータが存在する場合は挿入しないように、IDを明示的に指定し、ON CONFLICT DO NOTHING を使用します。
-- ただし、これはPostgreSQLの機能であり、他のDBでは異なる場合があります。
-- 初回起動時のみ実行されることを想定しています。

INSERT INTO products (id, name, price, image_url, is_sold) VALUES
(1, 'クラシックな革靴', 18000, '/images/sample1.jpg', false),
(2, '快適なスニーカー', 12000, '/images/sample2.jpg', false),
(3, 'ヴィンテージ風腕時計', 25000, '/images/sample3.jpg', false),
(4, 'おしゃれなバックパック', 8500, '/images/sample4.jpg', true),
(5, 'サングラス', 6000, '/images/sample5.jpg', false),
(6, 'ワイヤレスイヤホン', 15000, '/images/sample6.jpg', false)
ON CONFLICT (id) DO NOTHING;

-- シーケンスの更新 (PostgreSQLの場合)
-- これがないと、手動でIDを挿入した後に自動採番が重複する可能性があります。
SELECT setval('products_id_seq', (SELECT MAX(id) FROM products));