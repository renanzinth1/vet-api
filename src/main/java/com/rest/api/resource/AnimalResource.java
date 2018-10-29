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
import com.rest.api.repository.IAnimal;

@RestController
@RequestMapping("/animais")
public class AnimalResource {
	
	@Autowired
	private IAnimal animais;
	
	@GetMapping
	public ResponseEntity<List<Animal>> listar() {
		
		List<Animal> listaAnimal = animais.findAllByOrderByNomeAsc();
		
		return ResponseEntity.ok(listaAnimal);
	}
	
	@GetMapping(value = "/{codigo}")
	public ResponseEntity<Animal> buscar(@PathVariable("codigo") Long codigo) {
		
		Optional<Animal> animal = animais.findById(codigo);
		
		if(animal.isPresent())
			return ResponseEntity.ok(animal.get());
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(value = "/nome/{nome}")
	public ResponseEntity<List<Animal>> buscarPorNome(@PathVariable("nome") String nome) {
		
		List<Animal> listaAnimal = animais.findAllByNomeContainingIgnoreCaseOrderByNomeAsc(nome);
		
		if(listaAnimal.isEmpty())
			return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(listaAnimal);
	}
	
	@PostMapping
	public ResponseEntity<Void> salvar(@RequestBody Animal animal) {
		animal = animais.save(animal);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{codigo}")
				.buildAndExpand(animal.getCodigo())
				.toUri();
				
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
