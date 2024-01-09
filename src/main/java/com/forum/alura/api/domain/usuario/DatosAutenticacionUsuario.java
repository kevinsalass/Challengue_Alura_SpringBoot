package com.forum.alura.api.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record DatosAutenticacionUsuario(@NotBlank String username, @NotBlank String password) {

}