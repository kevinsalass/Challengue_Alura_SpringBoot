package com.forum.alura.api.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.forum.alura.api.domain.usuario.DatosRegistroUsuario;
import com.forum.alura.api.domain.usuario.DatosRespuestaUsuario;
import com.forum.alura.api.domain.usuario.Usuario;
import com.forum.alura.api.domain.usuario.UsuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@PostMapping
	public ResponseEntity<DatosRespuestaUsuario> registroUsuario(
			@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario, UriComponentsBuilder uriComponentsBuilder) {

		
		 // Codificar la contraseña antes de almacenarla
	    String contrasenaCodificada = passwordEncoder.encode(datosRegistroUsuario.password());

	    Usuario usuario = new Usuario(datosRegistroUsuario);
	    usuario.setPassword(contrasenaCodificada);

	    // Guardar el usuario en la base de datos
	    Usuario usuarioGuardado = usuarioRepository.save(usuario);

	    URI url = uriComponentsBuilder.path("usuarios/{id}").buildAndExpand(usuarioGuardado.getId()).toUri();
	    return ResponseEntity.created(url).body(new DatosRespuestaUsuario(usuarioGuardado));
		
	}

}
