-- liquibase formatted sql

-- changeset gymflow:1
CREATE TABLE reservas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_socio INT NOT NULL,
    fecha_hora TIMESTAMP NOT NULL,
    tipo_clase VARCHAR(100) NOT NULL,
    estado VARCHAR(50) NOT NULL
);

-- changeset gymflow:2
INSERT INTO reservas (id_socio, fecha_hora, tipo_clase, estado) VALUES
(1, '2024-06-01 10:00:00', 'Yoga', 'Confirmada'),
(2, '2024-06-01 11:00:00', 'Crossfit', 'Pendiente'),
(3, '2024-06-02 09:00:00', 'Zumba', 'Confirmada'),
(4, '2024-06-02 18:00:00', 'Spinning', 'Cancelada'),
(5, '2024-06-03 10:00:00', 'Boxeo', 'Confirmada'),
(6, '2024-06-03 12:00:00', 'Pilates', 'Confirmada'),
(7, '2024-06-04 15:00:00', 'GAP', 'Pendiente'),
(8, '2024-06-04 20:00:00', 'Funcional', 'Confirmada'),
(9, '2024-06-05 08:00:00', 'Natación', 'Confirmada'),
(10, '2024-06-05 19:00:00', 'Power', 'Confirmada');
