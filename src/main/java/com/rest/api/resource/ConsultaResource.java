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

import com.rest.api.model.Consulta;
import com.rest.api.model.Tratamento;
import com.rest.api.repository.IConsulta;
import com.rest.api.repository.ITratamento;

@RestController
@RequestMapping("/consultas")
public class ConsultaResource {
	
	@Autowired
	private IConsulta consultas;
	
	@Autowired
	private ITratamento tratamentos;
	
	@GetMapping
	public ResponseEntity<List<Consulta>> listar() {
		
		List<Consulta> listaConsulta = consultas.findAllByOrderByCodigoAsc();
		
		return ResponseEntity.ok(listaConsulta);
	}
	
	@GetMapping(value = "/{codigo}")
	public ResponseEntity<Consulta> buscar(@PathVariable("codigo") Long codigo) {
		
		Optional<Consulta> consulta = consultas.findById(codigo);
		
		if(consulta.isPresent())
			return ResponseEntity.ok(consulta.get());
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(value = "/{codigo}/tratamentos")
	public ResponseEntity<List<Tratamento>> buscarTratamentos(@PathVariable("codigo") Long codigo) {
		
		if(consultas.existsById(codigo)) {
			Optional<Consulta> consulta = consultas.findById(codigo);
			
			return ResponseEntity.ok(consulta.get().getListaTratamentos());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(value = "/{codigo}/tratamentos/{codigoTratamento}")
	public ResponseEntity<Tratamento> buscarTratamento(@PathVariable("codigo") Long codigo, @PathVariable("codigoTratamento") Long codigoTratamento) {
		
		if(consultas.existsById(codigo)) {
			
			Optional<Consulta> consulta = consultas.findById(codigo);
			
			Optional<Tratamento> tratamento = tratamentos.findById(codigoTratamento);
			
			if(tratamento.isPresent())
				if(consulta.get().getListaTratamentos().contains(tratamento.get()))
					return ResponseEntity.ok(tratamento.get());
		
		}
		return ResponseEntity.notFound().build();
		
	}
	
	@PostMapping
	public ResponseEntity<Void> salvar(@RequestBody Consulta consulta) {
		
		if(consultas.existsById(consulta.getCodigo()))
			return ResponseEntity.badRequest().build();
		
		consulta = consultas.save(consulta);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{codigo}")
				.buildAndExpand(consulta.getCodigo())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{codigo}")
	public ResponseEntity<Consulta> editar(@PathVariable("codigo") Long codigo, @RequestBody Consulta consulta){
		
		if(consultas.existsById(codigo)) {
			consulta.setCodigo(codigo);
			
			return ResponseEntity.accepted().body(consultas.save(consulta));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping(value = "/{codigo}")
	public ResponseEntity<Void> excluir(@PathVariable("codigo") Long codigo) {
		
		if(consultas.existsById(codigo)) {
			consultas.deleteById(codigo);
			
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.notFound().build();
	}

}
