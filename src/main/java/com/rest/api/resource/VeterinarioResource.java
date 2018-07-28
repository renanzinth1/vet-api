package com.rest.api.resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rest.api.model.Veterinario;
import com.rest.api.repository.IVeterinario;

@RestController
@RequestMapping("/veterinarios")
public class VeterinarioResource {
	
	@Autowired
	private IVeterinario veterinarios;
	
	@GetMapping
	public ResponseEntity<List<Veterinario>> listar() {
		return ResponseEntity.ok(veterinarios.findAll());
	}
	
	@PostMapping
	public ResponseEntity<Void> salvar(@RequestBody Veterinario veterinario) {
		
		boolean isPresent = veterinarios.findByCfmv(veterinario.getCfmv()).isPresent();
		
		if(isPresent)
			return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
		
		veterinario = veterinarios.save(veterinario);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.pathSegment("/{cfmv}")
				.buildAndExpand(veterinario.getCfmv())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}

}
