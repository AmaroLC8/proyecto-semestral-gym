-- liquibase formatted sql

-- changeset gymflow:1
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    correo VARCHAR(100),
    telefono VARCHAR(20),
    tipo_membresia VARCHAR(50)
);


-- changeset gymflow:2
INSERT INTO usuarios (nombre, correo, telefono, tipo_membresia) VALUES 
('Alexis Sanchez', 'alexis@gym.com', '+5691111', 'Oro'),
('Arturo Vidal', 'king@gym.com', '+5692222', 'Plata'),
('Claudio Bravo', 'capitan@gym.com', '+5693333', 'Oro'),
('Gary Medel', 'pitbull@gym.com', '+5694444', 'Bronce'),
('Ben Brereton', 'bigben@gym.com', '+5695555', 'Oro'),
('Charles Aranguiz', 'principe@gym.com', '+5696666', 'Plata'),
('Eduardo Vargas', 'turboman@gym.com', '+5697777', 'Plata'),
('Mauricio Isla', 'huaso@gym.com', '+5698888', 'Bronce'),
('Marcelino Nuñez', 'marce@gym.com', '+5699999', 'Bronce'),
('Erick Pulgar', 'erick@gym.com', '+5690000', 'Plata');
