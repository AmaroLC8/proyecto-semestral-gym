-- liquibase formatted sql

-- changeset gym:1
CREATE TABLE rutina (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    nivel VARCHAR(50)
);

CREATE TABLE ejercicio (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    series INT,
    repeticiones INT,
    rutina_id BIGINT,
    CONSTRAINT fk_ejercicio_rutina FOREIGN KEY (rutina_id) REFERENCES rutina(id)
);

-- changeset gym:2
INSERT INTO rutina (nombre, nivel) VALUES 
('Full Body A', 'Principiante'), ('Hipertrofia Piernas', 'Avanzado'), ('Torso-Pierna', 'Intermedio'), 
('Push-Pull', 'Avanzado'), ('Cardio HIIT', 'Principiante'), ('Yoga Flow', 'Intermedio'), 
('Crossfit WOD', 'Avanzado'), ('Resistencia', 'Intermedio'), ('Powerlifting', 'Avanzado'), ('Movilidad', 'Principiante');

-- changeset gym:3
INSERT INTO ejercicio (nombre, series, repeticiones, rutina_id) VALUES 
('Sentadillas', 4, 12, 1), ('Press Banca', 3, 10, 1), ('Peso Muerto', 3, 8, 2), 
('Prensa', 4, 15, 2), ('Dominadas', 3, 10, 3), ('Remo con barra', 4, 12, 3), 
('Press Militar', 3, 10, 4), ('Fondos', 3, 12, 4), ('Burpees', 5, 20, 5), ('Saltos al cajón', 5, 15, 5);