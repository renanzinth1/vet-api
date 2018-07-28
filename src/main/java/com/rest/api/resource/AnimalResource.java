package com.rest.api.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		animal = new Animal(null, animal.getNome(), animal.getDataNascimento(), animal.getSexo());

		animal = animais.save(animal);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{codigo}")
				.buildAndExpand(animal.getCodigo()).toUri();
				
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping(value = "/{nome}")
	public ResponseEntity<?> buscarPorNome(@PathVariable("nome") String nome) {
		
		Optional<Animal> animal = animais.findByNome(nome);
		
		if(animal.isPresent())
			return ResponseEntity.status(HttpStatus.OK).body(animal);
		
		return ResponseEntity.notFound().build();
	}

}
