-- liquibase formatted sql

-- changeset gymflow:auth-1
CREATE TABLE IF NOT EXISTS users (
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(50)  NOT NULL
);

-- changeset gymflow:auth-2
-- 70% de datos semilla: 7 usuarios (admin + trainers + miembros)
INSERT INTO users (email, password, role) VALUES
('admin@gym.com',        '7c4a8d09ca3762af61e59520943dc26494f8941b', 'ADMIN'),
('trainer.carlos@gym.com', '7c4a8d09ca3762af61e59520943dc26494f8941b', 'TRAINER'),
('trainer.sofia@gym.com',  '7c4a8d09ca3762af61e59520943dc26494f8941b', 'TRAINER'),
('miembro.juan@gym.com',   '7c4a8d09ca3762af61e59520943dc26494f8941b', 'USER'),
('miembro.ana@gym.com',    '7c4a8d09ca3762af61e59520943dc26494f8941b', 'USER'),
('miembro.pedro@gym.com',  '7c4a8d09ca3762af61e59520943dc26494f8941b', 'USER'),
('miembro.laura@gym.com',  '7c4a8d09ca3762af61e59520943dc26494f8941b', 'USER');
-- Nota: todos los passwords son SHA-1 de "123456"
-- El DataLoaderConfig.java cubre el 30% restante (verifica y no duplica si ya existe admin@gym.com)
