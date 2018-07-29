package com.rest.api.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
		return ResponseEntity.ok(animais.findAll());
	}
	
	@GetMapping(value = "/{nome}")
	public ResponseEntity<Animal> buscarPorNome(@PathVariable("nome") String nome) {
		
		Optional<Animal> animal = animais.findByNome(nome);
		
		if(animal.isPresent())
			return ResponseEntity.ok(animal.get());
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Void> salvar(@RequestBody Animal animal) {
		animal = animais.save(animal);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{codigo}")
				.buildAndExpand(animal.getCodigo()).toUri();
				
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value= "/{codigo}")
	public ResponseEntity<Animal> editar(@PathVariable("codigo") Long codigo, @RequestBody Animal animal){
		if(animais.existsById(codigo)) {
			animal.setCodigo(codigo);
			return ResponseEntity.accepted().body(animais.save(animal));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping(value="/{codigo}")
	public ResponseEntity<Void> excluir(@PathVariable("codigo") Long codigo){
		if(animais.existsById(codigo)) {
			animais.deleteById(codigo);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

}
