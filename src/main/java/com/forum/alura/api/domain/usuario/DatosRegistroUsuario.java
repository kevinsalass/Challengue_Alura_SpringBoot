package com.forum.alura.api.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record DatosRegistroUsuario(@NotBlank String username, @NotBlank String password) {
		
}
