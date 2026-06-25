-- liquibase formatted sql

-- changeset gymflow:1 endDelimiter:/
CREATE TABLE recomendaciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mensaje VARCHAR(500) NOT NULL,
    id_socio BIGINT NOT NULL
);
/

-- changeset gymflow:2 endDelimiter:/
INSERT INTO recomendaciones (mensaje, id_socio) VALUES
('Corre más para mejorar tu resistencia.', 1),
('Incluye entrenamiento de fuerza al menos 2 veces por semana.', 1),
('Descansa bien y mantente hidratado.', 1),
('Realiza estiramientos suaves antes y después de entrenar.', 1),
('Aumenta gradualmente la intensidad y escucha a tu cuerpo.', 1);
/