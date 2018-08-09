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

import com.rest.api.model.Medicamento;
import com.rest.api.repository.IMedicamento;

@RestController
@RequestMapping("/medicamentos")
public class MedicamentoResource {
	
	@Autowired
	private IMedicamento medicamentos;

	@GetMapping
	public ResponseEntity<List<Medicamento>> listar() {
		
		List<Medicamento> listaMedicamento = medicamentos.findAll();
		
		if(listaMedicamento.isEmpty())
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(listaMedicamento);
	}
	
	@GetMapping(value = "/{codigo}")
	public ResponseEntity<Medicamento> buscar(@PathVariable("codigo") Long codigo) {
		
		Optional<Medicamento> medicamento = medicamentos.findById(codigo);
		
		if(medicamento.isPresent())
			return ResponseEntity.ok(medicamento.get());
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(value = "/nome/{nome}")
	public ResponseEntity<List<Medicamento>> buscarPorNome(@PathVariable("nome") String nome){
		
		List<Medicamento> listaMedicamento = medicamentos.findAllByNomeContainingIgnoreCaseOrderByNomeAsc(nome);
		
		if(listaMedicamento.isEmpty())
			return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(listaMedicamento);
	}
	
	@PostMapping
	public ResponseEntity<Void> salvar(@RequestBody Medicamento medicamento) {
		
		if(medicamentos.existsByNome(medicamento.getNome()))
			return ResponseEntity.badRequest().build();
			
		medicamento = medicamentos.save(medicamento);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{codigo}")
				.buildAndExpand(medicamento.getCodigo())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{codigo}")
	public ResponseEntity<Medicamento> editar(@PathVariable("codigo") Long codigo, @RequestBody Medicamento medicamento){
		if(medicamentos.existsById(codigo)) {
			medicamento.setCodigo(codigo);
			return ResponseEntity.accepted().body(medicamentos.save(medicamento));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping(value="/{codigo}")
	public ResponseEntity<Void> excluir(@PathVariable("codigo") Long codigo){
		if(medicamentos.existsById(codigo)) {
			medicamentos.deleteById(codigo);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
}
