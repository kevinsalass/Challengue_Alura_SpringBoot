package com.forum.alura.api.domain.topico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroTopico(
		@NotBlank(message = "Titulo es obligatorio")
		String titulo,
		@NotBlank
		String mensaje,
		@NotNull
		Long autorID,
		@NotNull
		Estatus estatus,
		@NotBlank
		String curso) {
}
