-- liquibase formatted sql

-- changeset inventario:1 endDelimiter:/
CREATE TABLE inventarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    descripcion VARCHAR(500),
    cantidad INT NOT NULL,
    disponible BOOLEAN NOT NULL DEFAULT TRUE,
    categoria VARCHAR(100)
);
/

-- changeset inventario:2 endDelimiter:/
INSERT INTO inventarios (nombre, descripcion, cantidad, disponible, categoria) VALUES
('Pesas libres', 'Juego de mancuernas de 1 a 10 kg', 20, TRUE, 'Fuerza'),
('Colchonetas', 'Colchonetas de yoga y ejercicio', 30, TRUE, 'Accesorios'),
('Bicicleta estática', 'Bicicleta fija para entrenamiento cardiovascular', 5, TRUE, 'Cardio'),
('Balón medicinal', 'Balón medicinal de 5 kg', 10, TRUE, 'Fuerza'),
('Bandas elásticas', 'Set de bandas elásticas de resistencia', 25, TRUE, 'Accesorios');
/
