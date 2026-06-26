-- liquibase formatted sql

-- changeset inventario:1 endDelimiter:/
CREATE TABLE productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    stock INT NOT NULL,
    precio DOUBLE,
    categoria VARCHAR(100)
);
/

-- changeset inventario:2 endDelimiter:/
INSERT INTO productos (nombre, stock, precio, categoria) VALUES
('Pesas libres', 20, 15000.0, 'Fuerza'),
('Colchonetas', 30, 5000.0, 'Accesorios'),
('Bicicleta estática', 5, 120000.0, 'Cardio'),
('Balón medicinal', 10, 20000.0, 'Fuerza'),
('Bandas elásticas', 25, 8000.0, 'Accesorios');
/
