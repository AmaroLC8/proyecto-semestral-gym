-- liquibase formatted sql

-- changeset review:init endDelimiter:/
CREATE TABLE reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    mensaje VARCHAR(1000) NOT NULL,
    fecha DATETIME
);
/

-- changeset review:insert-init endDelimiter:/
INSERT INTO reviews (nombre, mensaje, fecha) VALUES
('Usuario prueba', 'Me gusta la web, buen diseño y contenido.', '2026-06-20 12:00:00');
/