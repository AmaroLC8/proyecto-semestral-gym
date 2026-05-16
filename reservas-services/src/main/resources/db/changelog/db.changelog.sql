-- liquibase formatted sql

-- changeset gymflow:1
CREATE TABLE clase (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_clase VARCHAR(100) NOT NULL,
    instructor VARCHAR(100)
);

CREATE TABLE reserva (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_socio BIGINT NOT NULL, -- Referencia Lógica (API) [6]
    clase_id BIGINT,
    fecha_hora TIMESTAMP NOT NULL,
    estado VARCHAR(50),
    CONSTRAINT fk_reserva_clase FOREIGN KEY (clase_id) REFERENCES clase(id)
);

-- changeset gymflow:2
INSERT INTO clase (nombre_clase, instructor) VALUES 
('Yoga', 'Maria Paz'), ('Crossfit', 'Juan Pablo'), ('Zumba', 'Carla'), ('Spinning', 'Roberto'), ('Boxeo', 'Luis'),
('Pilates', 'Ana'), ('GAP', 'Sonia'), ('Funcional', 'Pedro'), ('Natación', 'Miguel'), ('Power', 'Diego');

-- changeset gymflow:3
INSERT INTO reserva (id_socio, clase_id, fecha_hora, estado) VALUES 
(1, 1, '2024-06-01 10:00:00', 'Confirmada'), (2, 2, '2024-06-01 11:00:00', 'Pendiente'),
(3, 3, '2024-06-02 09:00:00', 'Confirmada'), (4, 4, '2024-06-02 18:00:00', 'Cancelada'),
(5, 5, '2024-06-03 10:00:00', 'Confirmada'), (6, 6, '2024-06-03 12:00:00', 'Confirmada'),
(7, 7, '2024-06-04 15:00:00', 'Pendiente'), (8, 8, '2024-06-04 20:00:00', 'Confirmada'),
(9, 9, '2024-06-05 08:00:00', 'Confirmada'), (10, 10, '2024-06-05 19:00:00', 'Confirmada');