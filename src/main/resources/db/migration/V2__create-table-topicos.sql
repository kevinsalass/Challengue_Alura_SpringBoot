CREATE TABLE topicos (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  titulo VARCHAR(150) NOT NULL,
  mensaje VARCHAR(255) NOT NULL,
  fecha_creacion DATE NOT NULL,
  estatus VARCHAR(255),
  autor_id BIGINT,
  CONSTRAINT fk_topicos_autor
    FOREIGN KEY (autor_id)
    REFERENCES usuarios (id)
);