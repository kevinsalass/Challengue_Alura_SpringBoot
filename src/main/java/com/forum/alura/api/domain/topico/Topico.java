package com.forum.alura.api.domain.topico;

import java.time.LocalDateTime;

import com.forum.alura.api.domain.usuario.Usuario;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String mensaje;
	private LocalDateTime fecha_creacion = LocalDateTime.now();

	private Boolean activo;
	@Enumerated(EnumType.STRING)
	private Estatus estatus = Estatus.No_RESPONDIDO;
	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario autor;
	private String curso;

	public Topico(DatosRegistroTopico datosRegistroTopico, Usuario autor) {
		this.activo = true;
		this.titulo = datosRegistroTopico.titulo();
		this.mensaje = datosRegistroTopico.mensaje();
		this.autor = autor;
		this.estatus = datosRegistroTopico.estatus();
		this.curso = datosRegistroTopico.curso();
	}

	public Topico(Long id, String titulo, String mensaje, Usuario autor, Estatus estatus, String curso,
			LocalDateTime fechaCreacion) {
		this.id = id;
		this.titulo = titulo;
		this.mensaje = mensaje;
		this.autor = autor;
		this.estatus = estatus;
		this.curso = curso;
		this.fecha_creacion = fechaCreacion;
	}

	public void desactivarTopico() {
		this.activo = false;
	}

	public void actualizarDatos(DatosActualizarTopico datosActualizarTopico) {
		if (datosActualizarTopico.titulo() != null) {
			this.titulo = datosActualizarTopico.titulo();
		}
		if (datosActualizarTopico.mensaje() != null) {
			this.mensaje = datosActualizarTopico.mensaje();
		}
		if (datosActualizarTopico.curso() != null) {
			this.curso =datosActualizarTopico.curso();
		}
	}

}
