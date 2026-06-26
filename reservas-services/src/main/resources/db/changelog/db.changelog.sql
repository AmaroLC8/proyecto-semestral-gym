-- liquibase formatted sql

-- changeset gymflow:1 endDelimiter:/
CREATE TABLE reservas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,
    fecha_reserva DATETIME,
    estado VARCHAR(50) NOT NULL
);
/

-- changeset gymflow:2 endDelimiter:/
INSERT INTO reservas (id_usuario, id_producto, fecha_reserva, estado) VALUES
(1, 1, '2026-06-01 10:00:00', 'Confirmada'),
(2, 2, '2026-06-01 11:00:00', 'Pendiente'),
(3, 3, '2026-06-02 09:00:00', 'Confirmada'),
(4, 4, '2026-06-02 18:00:00', 'Cancelada'),
(5, 5, '2026-06-03 10:00:00', 'Confirmada');
/
