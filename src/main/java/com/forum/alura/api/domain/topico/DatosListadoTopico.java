package com.forum.alura.api.domain.topico;

import java.time.LocalDateTime;

import com.forum.alura.api.domain.usuario.DatosRespuestaUsuario;


public record DatosListadoTopico(Long id, String titulo, String mensaje, LocalDateTime fechaCreacion, String estatus,
		DatosRespuestaUsuario autor, String curso) {
	public DatosListadoTopico(Topico topico) {
		this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFecha_creacion(),
				topico.getEstatus().toString(),
				new DatosRespuestaUsuario(topico.getAutor().getId(), topico.getAutor().getUsername()), 
				topico.getCurso());
	}
}
