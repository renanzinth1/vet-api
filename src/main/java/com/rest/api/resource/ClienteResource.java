package com.rest.api.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
		return new ResponseEntity<List<Cliente>>(clientes.findAll(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Void> salvar(@RequestBody Cliente cliente) {
		
		boolean isPresent = clientes.findByCpf(cliente.getCpf()).isPresent();
		
		if(isPresent)
			return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
		
		cliente = clientes.save(cliente);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{codigo}")
				.buildAndExpand(cliente.getCodigo())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping(value = "/{cpf}")
	public ResponseEntity<?> buscarPorCpf(@PathVariable("cpf") String cpf) {
		
		Optional<Cliente> cliente = clientes.findByCpf(cpf);
		
		if(cliente.isPresent())
			return ResponseEntity.status(HttpStatus.OK).body(cliente);
		
		return ResponseEntity.notFound().build();
	}

}