package com.rest.api.resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
		return new ResponseEntity<List<Medicamento>>(medicamentos.findAll(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Void> salvar(@RequestBody Medicamento medicamento) {
		
		boolean isPresent = medicamentos.findByNome(medicamento.getNome()).isPresent();
		
		if(isPresent)
			return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
			
		medicamento = medicamentos.save(medicamento);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{codigo}")
				.buildAndExpand(medicamento.getCodigo())
				.toUri();
		
			return ResponseEntity.created(uri).build();
	}
}
