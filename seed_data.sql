-- =============================================================================
-- SCRIPT DE POBLADO DE DATOS (SEED) - EVENTIFY PLATFORM (CORREGIDO)
-- =============================================================================

USE defaultdb;

-- 1. ROLES
INSERT IGNORE INTO roles (id, name) VALUES (1, 'ROLE_USER');
INSERT IGNORE INTO roles (id, name) VALUES (2, 'ROLE_ADMIN');
INSERT IGNORE INTO roles (id, name) VALUES (3, 'ROLE_INSTRUCTOR');

-- 2. USUARIOS (Password: prueba123)
INSERT IGNORE INTO users (id, username, password, created_at, updated_at) VALUES 
(1, 'cliente_pro', '$2a$10$GXEWbOdy8HQYZEYN4GmY2.ycLZBixVdxJ9ZEY4n7uR6Li67qoL6P.', NOW(), NOW()),
(2, 'organizador_vip', '$2a$10$GXEWbOdy8HQYZEYN4GmY2.ycLZBixVdxJ9ZEY4n7uR6Li67qoL6P.', NOW(), NOW()),
(3, 'admin_eventify', '$2a$10$GXEWbOdy8HQYZEYN4GmY2.ycLZBixVdxJ9ZEY4n7uR6Li67qoL6P.', NOW(), NOW());

UPDATE users
SET password = '$2a$10$GXEWbOdy8HQYZEYN4GmY2.ycLZBixVdxJ9ZEY4n7uR6Li67qoL6P.',
    updated_at = NOW()
WHERE username IN ('cliente_pro', 'organizador_vip', 'admin_eventify');

-- 3. ASIGNACIÓN DE ROLES
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (2, 1);
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (3, 2);

-- 4. PERFILES
-- Nota: Se cambió 'email' por 'email_address' y se agregaron campos obligatorios
INSERT IGNORE INTO profiles (id, first_name, last_name, email_address, profile_type, street_address, street_number, city, postal_code, country, created_at, updated_at) VALUES 
(1, 'Juan', 'Perez', 'juan@example.com', 'HOSTER', 'Av. Primavera', '123', 'Lima', '15036', 'Peru', NOW(), NOW()),
(2, 'Eventos', 'Elegantes', 'contacto@eventoselegantes.com', 'ORGANIZER', 'Calle Las Begonias', '456', 'Lima', '15047', 'Peru', NOW(), NOW());

-- 5. EVENTOS SOCIALES
-- Nota: Los nombres de columnas en SocialEvent usan el prefijo del value object o el nombre del campo del record
INSERT IGNORE INTO social_events (id, title, event_date, customer_name, place, value_status, created_at, updated_at) VALUES 
(1, 'Boda de Ana y Luis', '2026-12-24', 'Juan Perez', 'Playa El Silencio', 'Active', NOW(), NOW()),
(2, 'Cumpleaños de Sofía', '2026-10-15', 'Juan Perez', 'Salón Happy Days', 'Active', NOW(), NOW());

-- 6. CATÁLOGOS DE SERVICIOS
INSERT IGNORE INTO service_catalogs (id, profile_id, title, description, category, price_from, price_to, created_at, updated_at) VALUES 
(1, 2, 'Catering Gourmet', 'Menú de 5 tiempos con maridaje', 'Alimentación', 50.00, 150.00, NOW(), NOW()),
(2, 2, 'Decoración Vintage', 'Mobiliario y flores estilo retro', 'Decoración', 200.00, 800.00, NOW(), NOW());

-- 7. RESEÑAS
INSERT IGNORE INTO reviews (id, profile_id, social_event_id, full_name, content, rating, social_event_date, created_at, updated_at) VALUES 
(1, 2, 1, 'Juan Perez', 'Excelente servicio, la comida estuvo increíble.', 5, '2026-12-24', NOW(), NOW()),
(2, 2, 2, 'Maria Garcia', 'Muy puntuales, aunque el precio es un poco elevado.', 4, '2026-10-15', NOW(), NOW());

-- 8. ÁLBUMES DE FOTOS
INSERT IGNORE INTO albums (id, profile_id, title, description, created_at, updated_at) VALUES 
(1, 2, 'Boda Real 2025', 'Fotos de nuestro evento más grande del año pasado', NOW(), NOW());

-- =============================================================================
-- DATOS COMPLEMENTARIOS PARA EL ORGANIZADOR VIP
-- Usuario IAM: organizador_vip / prueba123
-- Perfil de negocio: profiles.id = 2
-- =============================================================================

-- 9. PERFIL ORGANIZADOR ENRIQUECIDO
UPDATE profiles
SET first_name = 'Eventos',
    last_name = 'Elegantes',
    email_address = 'contacto@eventoselegantes.com',
    profile_type = 'ORGANIZER',
    street_address = 'Calle Las Begonias',
    street_number = '456',
    city = 'Lima',
    postal_code = '15047',
    country = 'Peru',
    updated_at = NOW()
WHERE id = 2;

-- 10. CATÁLOGO AMPLIADO DE SERVICIOS DEL ORGANIZADOR
INSERT IGNORE INTO service_catalogs (id, profile_id, title, description, category, price_from, price_to, created_at, updated_at) VALUES
(3, 2, 'Wedding Planner Integral', 'Planificación completa de bodas: cronograma, proveedores, decoración, coordinación del día del evento y seguimiento personalizado.', 'Planificación', 2500.00, 8500.00, NOW(), NOW()),
(4, 2, 'Producción de Eventos Corporativos', 'Diseño, logística y ejecución de conferencias, activaciones de marca, lanzamientos y cenas empresariales.', 'Corporativo', 3500.00, 12000.00, NOW(), NOW()),
(5, 2, 'Decoración Floral Premium', 'Arreglos florales, centros de mesa, arco ceremonial, ambientación de ingreso y estaciones fotográficas.', 'Decoración', 900.00, 4500.00, NOW(), NOW()),
(6, 2, 'Banquete y Catering Ejecutivo', 'Menús personalizados, estaciones gourmet, bocaditos, bebidas y servicio de mozos para eventos sociales y corporativos.', 'Alimentación', 1800.00, 9800.00, NOW(), NOW()),
(7, 2, 'Fotografía y Video de Evento', 'Cobertura profesional, sesión previa, video resumen, galería digital y edición de fotografías seleccionadas.', 'Audiovisual', 1200.00, 5200.00, NOW(), NOW());

-- 11. ÁLBUMES ADICIONALES DEL ORGANIZADOR
INSERT IGNORE INTO albums (id, profile_id, title, description, created_at, updated_at) VALUES
(2, 2, 'Bodas de Ensueño', 'Selección de bodas elegantes producidas por Eventos Elegantes.', NOW(), NOW()),
(3, 2, 'Eventos Corporativos Premium', 'Producciones corporativas, lanzamientos y cenas empresariales.', NOW(), NOW()),
(4, 2, 'Decoración y Ambientación', 'Detalles de mesas, flores, iluminación y espacios temáticos.', NOW(), NOW());

-- 12. FOTOS DE ÁLBUMES
INSERT INTO album_photos (album_id, photo_url)
SELECT 1, 'https://images.unsplash.com/photo-1519741497674-611481863552'
WHERE NOT EXISTS (SELECT 1 FROM album_photos WHERE album_id = 1 AND photo_url = 'https://images.unsplash.com/photo-1519741497674-611481863552');
INSERT INTO album_photos (album_id, photo_url)
SELECT 1, 'https://images.unsplash.com/photo-1523438885200-e635ba2c371e'
WHERE NOT EXISTS (SELECT 1 FROM album_photos WHERE album_id = 1 AND photo_url = 'https://images.unsplash.com/photo-1523438885200-e635ba2c371e');
INSERT INTO album_photos (album_id, photo_url)
SELECT 1, 'https://images.unsplash.com/photo-1464366400600-7168b8af9bc3'
WHERE NOT EXISTS (SELECT 1 FROM album_photos WHERE album_id = 1 AND photo_url = 'https://images.unsplash.com/photo-1464366400600-7168b8af9bc3');
INSERT INTO album_photos (album_id, photo_url)
SELECT 2, 'https://images.unsplash.com/photo-1519225421980-715cb0215aed'
WHERE NOT EXISTS (SELECT 1 FROM album_photos WHERE album_id = 2 AND photo_url = 'https://images.unsplash.com/photo-1519225421980-715cb0215aed');
INSERT INTO album_photos (album_id, photo_url)
SELECT 2, 'https://images.unsplash.com/photo-1507504031003-b417219a0fde'
WHERE NOT EXISTS (SELECT 1 FROM album_photos WHERE album_id = 2 AND photo_url = 'https://images.unsplash.com/photo-1507504031003-b417219a0fde');
INSERT INTO album_photos (album_id, photo_url)
SELECT 2, 'https://images.unsplash.com/photo-1520854221256-17451cc331bf'
WHERE NOT EXISTS (SELECT 1 FROM album_photos WHERE album_id = 2 AND photo_url = 'https://images.unsplash.com/photo-1520854221256-17451cc331bf');
INSERT INTO album_photos (album_id, photo_url)
SELECT 3, 'https://images.unsplash.com/photo-1511578314322-379afb476865'
WHERE NOT EXISTS (SELECT 1 FROM album_photos WHERE album_id = 3 AND photo_url = 'https://images.unsplash.com/photo-1511578314322-379afb476865');
INSERT INTO album_photos (album_id, photo_url)
SELECT 3, 'https://images.unsplash.com/photo-1505373877841-8d25f7d46678'
WHERE NOT EXISTS (SELECT 1 FROM album_photos WHERE album_id = 3 AND photo_url = 'https://images.unsplash.com/photo-1505373877841-8d25f7d46678');
INSERT INTO album_photos (album_id, photo_url)
SELECT 3, 'https://images.unsplash.com/photo-1540575467063-178a50c2df87'
WHERE NOT EXISTS (SELECT 1 FROM album_photos WHERE album_id = 3 AND photo_url = 'https://images.unsplash.com/photo-1540575467063-178a50c2df87');
INSERT INTO album_photos (album_id, photo_url)
SELECT 4, 'https://images.unsplash.com/photo-1478146896981-b80fe463b330'
WHERE NOT EXISTS (SELECT 1 FROM album_photos WHERE album_id = 4 AND photo_url = 'https://images.unsplash.com/photo-1478146896981-b80fe463b330');
INSERT INTO album_photos (album_id, photo_url)
SELECT 4, 'https://images.unsplash.com/photo-1511795409834-ef04bbd61622'
WHERE NOT EXISTS (SELECT 1 FROM album_photos WHERE album_id = 4 AND photo_url = 'https://images.unsplash.com/photo-1511795409834-ef04bbd61622');
INSERT INTO album_photos (album_id, photo_url)
SELECT 4, 'https://images.unsplash.com/photo-1533174072545-7a4b6ad7a6c3'
WHERE NOT EXISTS (SELECT 1 FROM album_photos WHERE album_id = 4 AND photo_url = 'https://images.unsplash.com/photo-1533174072545-7a4b6ad7a6c3');

-- 13. EVENTOS SOCIALES PARA PROBAR COTIZACIONES Y RESEÑAS
INSERT IGNORE INTO social_events (id, title, event_date, customer_name, place, value_status, created_at, updated_at) VALUES
(3, 'Matrimonio de Valeria y Diego', '2026-09-19', 'Valeria Torres', 'Casa Hacienda Los Ficus', 'Active', NOW(), NOW()),
(4, 'Lanzamiento Marca Aurora', '2026-08-06', 'Camila Rojas', 'Centro de Convenciones Lima', 'Active', NOW(), NOW()),
(5, 'Aniversario Corporativo NovaTech', '2026-11-12', 'Ricardo Salazar', 'Hotel Country Club', 'Active', NOW(), NOW()),
(6, 'Quinceañero de Isabella', '2026-07-25', 'Patricia Mendoza', 'Jardines del Sol', 'Completed', NOW(), NOW());

-- 14. COTIZACIONES DEL ORGANIZADOR VIP
INSERT IGNORE INTO quotes (quote_id, title, event_type, guest_quantity, location, total_price, state, event_date, organizer_id, host_id, created_at, updated_at) VALUES
('quote-orgvip-001', 'Boda elegante en hacienda', 'WEDDING', 180, 'Casa Hacienda Los Ficus', 14850.00, 'PENDING', '2026-09-19 18:00:00', 2, 1, NOW(), NOW()),
('quote-orgvip-002', 'Lanzamiento de marca premium', 'CONFERENCE', 220, 'Centro de Convenciones Lima', 19600.00, 'ACCEPTED', '2026-08-06 09:00:00', 2, 1, NOW(), NOW()),
('quote-orgvip-003', 'Aniversario corporativo NovaTech', 'CONFERENCE', 150, 'Hotel Country Club', 11200.00, 'PENDING', '2026-11-12 19:30:00', 2, 1, NOW(), NOW()),
('quote-orgvip-004', 'Quinceañero temático Isabella', 'BIRTHDAY', 120, 'Jardines del Sol', 8900.00, 'REJECTED', '2026-07-25 17:00:00', 2, 1, NOW(), NOW());

-- 15. ITEMS DE COTIZACIÓN DEL ORGANIZADOR VIP
INSERT IGNORE INTO service_items (service_item_id, description, quantity, unit_price, total_price, quote_id, created_at, updated_at) VALUES
('item-orgvip-001', 'Planificación integral y coordinación del día del evento', 1, 4200.00, 4200.00, 'quote-orgvip-001', NOW(), NOW()),
('item-orgvip-002', 'Decoración floral premium para ceremonia y recepción', 1, 3800.00, 3800.00, 'quote-orgvip-001', NOW(), NOW()),
('item-orgvip-003', 'Catering gourmet para 180 invitados', 180, 38.00, 6840.00, 'quote-orgvip-001', NOW(), NOW()),
('item-orgvip-004', 'Producción general y logística técnica', 1, 6500.00, 6500.00, 'quote-orgvip-002', NOW(), NOW()),
('item-orgvip-005', 'Coffee break premium para asistentes', 220, 28.00, 6160.00, 'quote-orgvip-002', NOW(), NOW()),
('item-orgvip-006', 'Escenario, iluminación y branding de marca', 1, 6940.00, 6940.00, 'quote-orgvip-002', NOW(), NOW()),
('item-orgvip-007', 'Cena corporativa de tres tiempos', 150, 42.00, 6300.00, 'quote-orgvip-003', NOW(), NOW()),
('item-orgvip-008', 'Ambientación institucional y centros de mesa', 1, 2400.00, 2400.00, 'quote-orgvip-003', NOW(), NOW()),
('item-orgvip-009', 'Fotografía y video resumen del evento', 1, 2500.00, 2500.00, 'quote-orgvip-003', NOW(), NOW()),
('item-orgvip-010', 'Decoración temática y mesa principal', 1, 3600.00, 3600.00, 'quote-orgvip-004', NOW(), NOW()),
('item-orgvip-011', 'Catering juvenil para 120 invitados', 120, 32.00, 3840.00, 'quote-orgvip-004', NOW(), NOW()),
('item-orgvip-012', 'DJ, luces y cabina de fotos', 1, 1460.00, 1460.00, 'quote-orgvip-004', NOW(), NOW());

-- 16. RESEÑAS ADICIONALES PARA EL ORGANIZADOR VIP
INSERT IGNORE INTO reviews (id, profile_id, social_event_id, full_name, content, rating, social_event_date, created_at, updated_at) VALUES
(3, 2, 3, 'Valeria Torres', 'El equipo fue muy detallista desde la primera reunión. La propuesta quedó clara y muy profesional.', 5, '2026-09-19', NOW(), NOW()),
(4, 2, 4, 'Camila Rojas', 'La producción del lanzamiento salió impecable. Buen manejo de tiempos, proveedores y ambientación.', 5, '2026-08-06', NOW(), NOW()),
(5, 2, 5, 'Ricardo Salazar', 'Excelente coordinación para el evento corporativo. El catering y la decoración estuvieron a la altura.', 4, '2026-11-12', NOW(), NOW()),
(6, 2, 6, 'Patricia Mendoza', 'Muy buena decoración y trato amable. Hubo pequeños retrasos al inicio, pero el resultado final fue bonito.', 4, '2026-07-25', NOW(), NOW());
