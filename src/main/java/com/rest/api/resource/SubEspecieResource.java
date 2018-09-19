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

import com.rest.api.model.SubEspecie;
import com.rest.api.repository.ISubEspecie;

@RestController
@RequestMapping("/subespecies")
public class SubEspecieResource {
	
	@Autowired
	private ISubEspecie subespecies;
	
	@GetMapping
	public ResponseEntity<List<SubEspecie>> listar() {
		
		List<SubEspecie> listaSubEspecie = subespecies.findAll();
		
		return ResponseEntity.ok(listaSubEspecie);
	}
	
	@GetMapping(value = "/{codigo}")
	public ResponseEntity<SubEspecie> buscar(@PathVariable("codigo") Long codigo) {
		
		Optional<SubEspecie> especie = subespecies.findById(codigo);
		
		if(especie.isPresent())
			return ResponseEntity.ok(especie.get());
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<SubEspecie>> buscarPorNome(@PathVariable("nome") String nome) {
		
		List<SubEspecie> listaSubEspecie = subespecies.findAllByNomeContainingIgnoreCaseOrderByNomeAsc(nome);
		
		if(listaSubEspecie.isEmpty())
			return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(listaSubEspecie);
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody SubEspecie subespecie) {
		
		if(subespecies.existsByNome(subespecie.getNome()))
			return ResponseEntity.badRequest().build();
		
		subespecie = subespecies.save(subespecie);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{codigo}")
				.buildAndExpand(subespecie.getCodigo())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{codigo}")
	public ResponseEntity<SubEspecie> editar(@PathVariable("codigo") Long codigo, SubEspecie subespecie) {
		
		if(subespecies.existsById(codigo)) {
			subespecie.setCodigo(codigo);
			subespecie = subespecies.save(subespecie);
			
			return ResponseEntity.accepted().body(subespecie);
		}
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{codigo}")
	public ResponseEntity<Void> excluir(@PathVariable("codigo") Long codigo) {
		
		if(subespecies.existsById(codigo)) {
			subespecies.deleteById(codigo);
			
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
