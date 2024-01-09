package com.forum.alura.api.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.forum.alura.api.domain.topico.DatosActualizarTopico;
import com.forum.alura.api.domain.topico.DatosListadoTopico;
import com.forum.alura.api.domain.topico.DatosRegistroTopico;
import com.forum.alura.api.domain.topico.DatosRespuestaTopico;
import com.forum.alura.api.domain.topico.Topico;
import com.forum.alura.api.domain.topico.TopicoRepository;
import com.forum.alura.api.domain.usuario.DatosRespuestaUsuario;
import com.forum.alura.api.domain.usuario.Usuario;
import com.forum.alura.api.domain.usuario.UsuarioRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

	@Autowired
	private TopicoRepository topicoRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping
	public ResponseEntity<DatosRespuestaTopico> registrartopico(
			@RequestBody @Valid DatosRegistroTopico datosRegistroTopico, BindingResult bindingResult,
			UriComponentsBuilder uriComponentsBuilder) {

		if (bindingResult.hasErrors()) {
			// Manejar errores de validación aquí
			return ResponseEntity.badRequest().build();
		}
		Usuario usuario = usuarioRepository.getReferenceById(datosRegistroTopico.autorID());
		Topico topico = topicoRepository.save(new Topico(datosRegistroTopico, usuario)); /////////
		usuario.agregarTopico(topico);

		URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(url).body(new DatosRespuestaTopico(topico));
	}

	@GetMapping
	public ResponseEntity<Page<DatosListadoTopico>> listadosTopicos(@PageableDefault(size = 2) Pageable paginacion) {
		return ResponseEntity.ok(topicoRepository.findByActivoTrue(paginacion).map(DatosListadoTopico::new));
	}

	@GetMapping("/{id}")
	public ResponseEntity<DatosListadoTopico> retornarListadoTopico(@PathVariable Long id) {
		Topico topico = topicoRepository.getReferenceById(id);
		var datosTopico = new DatosListadoTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(),
				topico.getFecha_creacion(), topico.getEstatus().toString(),
				new DatosRespuestaUsuario(topico.getAutor().getId(), topico.getAutor().getUsername()), topico.getCurso());
		return ResponseEntity.ok(datosTopico);
	}

	@PutMapping
	@Transactional
	public ResponseEntity actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
		Topico topico = topicoRepository.getReferenceById(datosActualizarTopico.id());
		topico.actualizarDatos(datosActualizarTopico);
		return ResponseEntity.ok(new DatosRespuestaTopico(topico));
	}
	

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity eliminarTopico(@PathVariable Long id) {
		Topico topico = topicoRepository.getReferenceById(id);
		topicoRepository.delete(topico);
		return ResponseEntity.noContent().build();
	}

}
