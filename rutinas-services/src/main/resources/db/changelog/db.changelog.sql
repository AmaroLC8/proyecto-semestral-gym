-- liquibase formatted sql

-- changeset gymflow:1 endDelimiter:/
CREATE TABLE rutinas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    duracion_minutos INT NOT NULL,
    nivel_dificultad VARCHAR(50) NOT NULL
);
/

-- changeset gymflow:2 endDelimiter:/
INSERT INTO rutinas (nombre, descripcion, duracion_minutos, nivel_dificultad) VALUES
('Full Body A', 'Rutina de cuerpo completo para comenzar.', 60, 'Principiante'),
('Hipertrofia Piernas', 'Trabajo enfocado en fuerza e hipertrofia de piernas.', 75, 'Avanzado'),
('Torso-Pierna', 'División equilibrada para tren superior e inferior.', 70, 'Intermedio'),
('Push-Pull', 'Entrenamiento dividido por patrones de empuje y tirón.', 65, 'Avanzado'),
('Cardio HIIT', 'Intervalos de alta intensidad para resistencia cardiovascular.', 30, 'Principiante'),
('Yoga Flow', 'Secuencia de movilidad y respiración.', 45, 'Intermedio'),
('Crossfit WOD', 'Circuito metabólico de alta intensidad.', 50, 'Avanzado'),
('Resistencia', 'Trabajo progresivo para mejorar resistencia muscular.', 55, 'Intermedio'),
('Powerlifting', 'Enfoque en sentadilla, banca y peso muerto.', 80, 'Avanzado'),
('Movilidad', 'Ejercicios correctivos y rango de movimiento.', 35, 'Principiante');
/
