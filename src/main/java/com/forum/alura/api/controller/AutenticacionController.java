package com.forum.alura.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forum.alura.api.domain.usuario.DatosAutenticacionUsuario;
import com.forum.alura.api.domain.usuario.Usuario;
import com.forum.alura.api.infra.security.DatosJWTToken;
import com.forum.alura.api.infra.security.TokenService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/login")
public class AutenticacionController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private TokenService tokenService;
	
	
	@PostMapping
	public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
		
		Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.username(), 
				datosAutenticacionUsuario.password());
		var usuarioAutenticado = authenticationManager.authenticate(authToken);
		var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
		return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
		
	}
	
}
