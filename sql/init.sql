-- 1. Insertar la Orden
INSERT INTO order_table (id, id_user, status, create_at, delete_at)
VALUES (
           '550e8400-e29b-41d4-a716-446655440001',
           '550e8400-e29b-41d4-a716-446655440004',
           'PENDING_TO_DO',
           CURRENT_TIMESTAMP,
           NULL
       )
    ON CONFLICT (id) DO NOTHING;

-- 2. Insertar Productos de la Orden
INSERT INTO products_order_table (id, id_order, id_product, quantity_products, create_at, delete_at)
VALUES (
           '550e8400-e29b-41d4-a716-446655440002',
           '550e8400-e29b-41d4-a716-446655440001',
           '550e8400-e29b-41d4-a716-446655440100',
           2,
           CURRENT_TIMESTAMP,
           NULL
       )
    ON CONFLICT (id) DO NOTHING;

