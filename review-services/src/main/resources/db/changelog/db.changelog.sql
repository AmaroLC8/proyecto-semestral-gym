-- liquibase formatted sql

-- changeset review:init endDelimiter:/
CREATE TABLE reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_producto BIGINT NOT NULL,
    calificacion INT NOT NULL,
    comentario VARCHAR(1000)
);
/

-- changeset review:insert-init endDelimiter:/
INSERT INTO reviews (id_producto, calificacion, comentario) VALUES
(1, 5, 'Excelente producto, muy recomendado.'),
(2, 4, 'Buena calidad pero podría mejorar el empaque.');
/