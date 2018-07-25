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

import com.rest.api.model.Animal;
import com.rest.api.repository.IAnimais;

@RestController
@RequestMapping("/animais")
public class AnimalResource {
	
	@Autowired
	private IAnimais animais;
	
	@GetMapping
	public ResponseEntity<List<Animal>> listar() {
		return new ResponseEntity<List<Animal>>(animais.findAll(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Void> salvar(@RequestBody Animal animal) {
		
		boolean isPresent = animais.findById(animal.getCodigo()).isPresent();
		
		if(isPresent)
			return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
		
		animal = animais.save(animal);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{codigo}")
				.buildAndExpand(animal.getCodigo()).toUri();
				
		return ResponseEntity.created(uri).build();
	}

}
