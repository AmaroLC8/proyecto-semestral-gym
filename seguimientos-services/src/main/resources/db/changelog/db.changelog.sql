-- liquibase formatted sql

-- changeset gymflow:1 endDelimiter:/
CREATE TABLE seguimientos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_socio BIGINT NOT NULL,
    peso DECIMAL(5,2),
    porcentaje_grasa DECIMAL(5,2),
    fecha_registro DATE
);
/

-- changeset gymflow:2 endDelimiter:/
INSERT INTO seguimientos (id_socio, peso, porcentaje_grasa, fecha_registro) VALUES 
(1, 80.5, 15.2, '2024-05-01'), (2, 75.0, 18.5, '2024-05-01'), (3, 90.2, 22.1, '2024-05-02'),
(4, 70.8, 12.4, '2024-05-02'), (5, 85.0, 14.0, '2024-05-03'), (1, 79.8, 14.9, '2024-05-15'),
(2, 74.2, 18.0, '2024-05-15'), (3, 89.5, 21.5, '2024-05-16'), (4, 71.0, 12.0, '2024-05-16'),
(5, 84.5, 13.8, '2024-05-17');
/