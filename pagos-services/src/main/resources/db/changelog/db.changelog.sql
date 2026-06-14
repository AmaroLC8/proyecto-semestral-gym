-- liquibase formatted sql

-- changeset gymflow:1 endDelimiter:/
CREATE TABLE pago (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_socio BIGINT NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    fecha_pago DATE NOT NULL,
    metodo_pago VARCHAR(50)
);
/

-- changeset gymflow:2 endDelimiter:/
INSERT INTO pago (id_socio, monto, fecha_pago, metodo_pago) VALUES 
(1, 35000, '2024-05-01', 'Tarjeta'), (2, 25000, '2024-05-01', 'Efectivo'), (3, 45000, '2024-05-02', 'Transferencia'),
(4, 30000, '2024-05-02', 'Tarjeta'), (5, 35000, '2024-05-03', 'Tarjeta'), (6, 25000, '2024-05-03', 'Efectivo'),
(7, 30000, '2024-05-04', 'Transferencia'), (8, 45000, '2024-05-04', 'Tarjeta'), (9, 35000, '2024-05-05', 'Tarjeta'),
(10, 25000, '2024-05-05', 'Efectivo');
/