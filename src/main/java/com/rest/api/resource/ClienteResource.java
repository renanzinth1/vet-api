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

import com.rest.api.model.Cliente;
import com.rest.api.repository.IClientes;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {
	
	@Autowired
	private IClientes clientes;
	
	@GetMapping
	public ResponseEntity<List<Cliente>> listar() {
		return ResponseEntity.ok(clientes.findAll());
	}
	
	@GetMapping(value = "/{codigo}")
	public ResponseEntity<Cliente> buscar(@PathVariable("codigo") Long codigo) {
		
		Optional<Cliente> cliente = clientes.findById(codigo);
		
		if(cliente.isPresent())
			return ResponseEntity.ok(cliente.get());
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(value = "/cpf/{cpf}")
	public ResponseEntity<Cliente> buscarPorCpf(@PathVariable("cpf") String cpf) {
		
		Optional<Cliente> cliente = clientes.findByCpf(cpf);
		
		if(cliente.isPresent())
			return ResponseEntity.ok(cliente.get());
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(value = "/nome/{nome}")
	public ResponseEntity<List<Cliente>> buscarPorNome(@PathVariable("nome") String nome){
		return ResponseEntity.ok(clientes.findAllByNomeContainingIgnoreCaseOrderByNomeAsc(nome));
	}
	
	@PostMapping
	public ResponseEntity<Void> salvar(@RequestBody Cliente cliente) {
		
		if(clientes.existsByCpf(cliente.getCpf()))
			return ResponseEntity.badRequest().build();
		
		cliente = clientes.save(cliente);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{codigo}")
				.buildAndExpand(cliente.getCodigo())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{codigo}")
	public ResponseEntity<Cliente> editar(@PathVariable("codigo") Long codigo, @RequestBody Cliente cliente){
		if(clientes.existsById(codigo)) {
			cliente.setCodigo(codigo);
			return ResponseEntity.accepted().body(clientes.save(cliente));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping(value="/{codigo}")
	public ResponseEntity<Void> excluir(@PathVariable("codigo") Long codigo){
		if(clientes.existsById(codigo)) {
			clientes.deleteById(codigo);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

}