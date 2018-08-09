package com.rest.api.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.rest.api.model.Especie;
import com.rest.api.repository.IEspecie;

@RestController
@RequestMapping("/especies")
public class EspecieResource {
	
	@Autowired
	private IEspecie especies;
	
	@GetMapping
	public ResponseEntity<List<Especie>> listar() {
		
		List<Especie> listaEspecie = especies.findAll();
		
		if(listaEspecie.isEmpty())
			return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(listaEspecie);
	}
	
	@GetMapping(value = "/{codigo}")
	public ResponseEntity<Especie> buscar(@PathVariable("codigo") Long codigo) {
		
		Optional<Especie> especie = especies.findById(codigo);
		
		if(especie.isPresent())
			return ResponseEntity.ok(especie.get());
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Especie>> buscarPorNome(@PathVariable("nome") String nome) {
		List<Especie> listaEspecie = especies.findAllByNomeContainingIgnoreCaseOrderByNomeAsc(nome);
		
		if(listaEspecie.isEmpty())
				return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(listaEspecie);
	}
	
	@PostMapping
	public ResponseEntity<Void> salvar(@RequestBody Especie especie) {
		
		if(especies.existsByNome(especie.getNome()))
			return ResponseEntity.badRequest().build();
		
		especie = especies.save(especie);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{codigo}")
				.buildAndExpand(especie.getCodigo())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{codigo}")
	public ResponseEntity<Especie> editar(@PathVariable("codigo") Long codigo,
			@RequestBody Especie especie) {
		
		if (especies.existsById(codigo)) {
			especie.setCodigo(codigo);
			return ResponseEntity.accepted().body(especies.save(especie));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping(value = "/{codigo}")
	public ResponseEntity<Void> excluir(@PathVariable("codigo") Long codigo) {
		
		if(especies.existsById(codigo)) {
			especies.deleteById(codigo);
			
			//Conversar c o Jardel se a resposta do HTTP é esta mesmo...
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

}
