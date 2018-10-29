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

import com.rest.api.model.Medicacao;
import com.rest.api.repository.IMedicacao;

@RestController
@RequestMapping("/medicacoes")
public class MedicacaoResource {
	
	@Autowired
	private IMedicacao medicacoes;
	
	@GetMapping
	public ResponseEntity<List<Medicacao>> listar() {
		return ResponseEntity.ok(medicacoes.findAll());
	}
	
	@GetMapping(value = "/{codigo}")
	public ResponseEntity<Medicacao> buscar(@PathVariable("codigo") Long codigo) {
		
		Optional<Medicacao> medicacao = medicacoes.findById(codigo);
		
		if(medicacao.isPresent())
			return ResponseEntity.ok(medicacao.get());
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Void> salvar(@RequestBody Medicacao medicacao) {
			
		medicacao = medicacoes.save(medicacao);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}")
				.buildAndExpand(medicacao.getCodigo()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{codigo}")
	public ResponseEntity<Medicacao> editar(@PathVariable("codigo") Long codigo, @RequestBody Medicacao medicacao){
		if(medicacoes.existsById(codigo)) {
			medicacao.setCodigo(codigo);
			return ResponseEntity.accepted().body(medicacoes.save(medicacao));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping(value="/{codigo}")
	public ResponseEntity<Void> excluir(@PathVariable("codigo") Long codigo){
		if(medicacoes.existsById(codigo)) {
			medicacoes.deleteById(codigo);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

}
