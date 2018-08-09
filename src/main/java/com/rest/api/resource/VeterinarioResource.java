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

import com.rest.api.model.Veterinario;
import com.rest.api.repository.IVeterinario;

@RestController
@RequestMapping("/veterinarios")
public class VeterinarioResource {
	
	@Autowired
	private IVeterinario veterinarios;
	
	@GetMapping
	public ResponseEntity<List<Veterinario>> listar() {
		
		List<Veterinario> listaVeterinario = veterinarios.findAll();
		
		if(listaVeterinario.isEmpty())
			return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(listaVeterinario);
	}
	
	@GetMapping(value = "/{codigo}")
	public ResponseEntity<Veterinario> buscar(@PathVariable("codigo") Long codigo) {
		
		Optional<Veterinario> veterinario = veterinarios.findById(codigo);
		
		if(veterinario.isPresent())
			return ResponseEntity.ok(veterinario.get());
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(value = "/cfmv/{cfmv}")
	public ResponseEntity<Veterinario> buscarPorCfmv(@PathVariable("cfmv") String cfmv) {
		
		Optional<Veterinario> veterinario = veterinarios.findByCfmv(cfmv);
		
		if(veterinario.isPresent())
			return ResponseEntity.ok(veterinario.get());
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(value = "/nome/{nome}")
	public ResponseEntity<List<Veterinario>> buscarPorNome(@PathVariable("nome") String nome){
		
		List<Veterinario> listaVeterinario = veterinarios.findAllByNomeContainingIgnoreCaseOrderByNomeAsc(nome);
		
		if(listaVeterinario.isEmpty())
			return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(listaVeterinario);
	}
	
	@PostMapping
	public ResponseEntity<Void> salvar(@RequestBody Veterinario veterinario) {
		
		if(veterinarios.existsByCfmv(veterinario.getCfmv()))
			return ResponseEntity.badRequest().build();
		
		veterinario = veterinarios.save(veterinario);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.pathSegment("/{cfmv}")
				.buildAndExpand(veterinario.getCodigo())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{codigo}")
	public ResponseEntity<Veterinario> editar(@PathVariable("codigo") Long codigo, @RequestBody Veterinario veterinario){
		if(veterinarios.existsById(codigo)) {
			veterinario.setCodigo(codigo);
			return ResponseEntity.accepted().body(veterinarios.save(veterinario));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping(value="/{codigo}")
	public ResponseEntity<Void> excluir(@PathVariable("codigo") Long codigo){
		if(veterinarios.existsById(codigo)) {
			veterinarios.deleteById(codigo);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

}
