package com.forum.alura.api.domain.topico;

import java.time.LocalDateTime;

import com.forum.alura.api.domain.usuario.DatosRespuestaUsuario;


public record DatosRespuestaTopico(Long id, String titulo, String mensaje, DatosRespuestaUsuario usuario,
		String curso) {

	public DatosRespuestaTopico(Topico topico) {
		this(topico.getId(), topico.getTitulo(), topico.getMensaje(),
					new DatosRespuestaUsuario(topico.getAutor().getId(), topico.getAutor().getUsername()),
					topico.getCurso());
	}


}
