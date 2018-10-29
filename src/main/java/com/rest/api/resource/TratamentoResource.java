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
import com.rest.api.model.Medicamento;
import com.rest.api.model.Tratamento;
import com.rest.api.repository.IMedicacao;
import com.rest.api.repository.ITratamento;

@RestController
@RequestMapping("/tratamentos")
public class TratamentoResource {
	
	@Autowired
	private ITratamento tratamentos;
	
	@Autowired
	private IMedicacao medicacoes;
	
	@GetMapping
	public ResponseEntity<List<Tratamento>> listar() {
		return ResponseEntity.ok(tratamentos.findAll());
	}
	
	@GetMapping(value = "/{codigo}")
	public ResponseEntity<Tratamento> buscar(@PathVariable("codigo") Long codigo) {
		
		Optional<Tratamento> tratamento = tratamentos.findById(codigo);
		
		if(tratamento.isPresent())
			return ResponseEntity.ok(tratamento.get());
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(value = "/{codigo}/medicacoes")
	public ResponseEntity<List<Medicacao>> buscarMedicacoes(@PathVariable("codigo") Long codigo) {

		if (tratamentos.existsById(codigo)) {
			Optional<Tratamento> tratamento = tratamentos.findById(codigo);

			return ResponseEntity.ok(tratamento.get().getListaMedicacoes());
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(value = "/{codigo}/medicacoes/{codigoMedicacao}")
	public ResponseEntity<Medicacao> buscarMedicacao(@PathVariable("codigo") Long codigo,
			@PathVariable("codigoMedicacao") Long codigoMedicacao) {

		if (tratamentos.existsById(codigo)) {
			Optional<Tratamento> tratamento = tratamentos.findById(codigo);

			Optional<Medicacao> medicacao = medicacoes.findById(codigoMedicacao);

			if (medicacao.isPresent()) {
				if (tratamento.get().getListaMedicacoes().contains(medicacao.get())) {
					return ResponseEntity.ok(medicacao.get());
				}
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Void> salvar(@RequestBody Tratamento tratamento) {
			
		tratamento = tratamentos.save(tratamento);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}")
				.buildAndExpand(tratamento.getCodigo()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{codigo}")
	public ResponseEntity<Tratamento> editar(@PathVariable("codigo") Long codigo, @RequestBody Tratamento tratamento){
		if(tratamentos.existsById(codigo)) {
			tratamento.setCodigo(codigo);
			return ResponseEntity.accepted().body(tratamentos.save(tratamento));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping(value="/{codigo}")
	public ResponseEntity<Void> excluir(@PathVariable("codigo") Long codigo){
		if(tratamentos.existsById(codigo)) {
			tratamentos.deleteById(codigo);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

}
