-- liquibase formatted SQL for soporte tickets
-- changeset carol:create-soporte-table
CREATE TABLE IF NOT EXISTS soporte (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  usuario_id BIGINT NOT NULL,
  asunto VARCHAR(1000) NOT NULL,
  descripcion TEXT,
  estado VARCHAR(50),
  fecha_creacion DATETIME,
  respuesta_admin TEXT
);
