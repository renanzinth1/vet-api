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

import com.rest.api.model.Especie;
import com.rest.api.model.SubEspecie;
import com.rest.api.repository.IEspecie;
import com.rest.api.repository.ISubEspecie;

@RestController
@RequestMapping("/especies")
public class EspecieResource {
	
	@Autowired
	private IEspecie especies;
	
	@Autowired
	private ISubEspecie subEspecies;
	
	@GetMapping
	public ResponseEntity<List<Especie>> listar() {
		
		List<Especie> listaEspecie = especies.findAllByOrderByNomeAsc();
		
		return ResponseEntity.ok(listaEspecie);
	}
	
	@GetMapping(value = "/{codigo}")
	public ResponseEntity<Especie> buscar(@PathVariable("codigo") Long codigo) {
		
		Optional<Especie> especie = especies.findById(codigo);
		
		if(especie.isPresent())
			return ResponseEntity.ok(especie.get());
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(value = "/{codigo}/subEspecies")
	public ResponseEntity<List<SubEspecie>> buscarSubEspecies(@PathVariable("codigo") Long codigo) {
		
		if(especies.existsById(codigo)) {
			Optional<Especie> especie = especies.findById(codigo);
			
			return ResponseEntity.ok(especie.get().getListaSubEspecies());
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(value = "/{codigo}/subEspecies/{codigoSubEspecie}")
	public ResponseEntity<SubEspecie> buscarSubEspecie(@PathVariable("codigo") Long codigo, @PathVariable("codigoSubEspecie") Long codigoSubEspecie) {
		
		if(especies.existsById(codigo)) {
			Optional<Especie> especie = especies.findById(codigo);
			
			Optional<SubEspecie> subEspecie = subEspecies.findById(codigoSubEspecie);
			
			if(subEspecie.isPresent()) {
				if(especie.get().getListaSubEspecies().contains(subEspecie.get())) {
					return ResponseEntity.ok(subEspecie.get());
				}
			}
		}
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
			
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.notFound().build();
	}

}
