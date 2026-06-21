-- liquibase formatted sql

-- changeset gymflow:1 endDelimiter:/
CREATE TABLE recomendaciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mensaje VARCHAR(500) NOT NULL
);
/

-- changeset gymflow:2 endDelimiter:/
INSERT INTO recomendaciones (mensaje) VALUES
('Corre más para mejorar tu resistencia.'),
('Incluye entrenamiento de fuerza al menos 2 veces por semana.'),
('Descansa bien y mantente hidratado.'),
('Realiza estiramientos suaves antes y después de entrenar.'),
('Aumenta gradualmente la intensidad y escucha a tu cuerpo.');
/