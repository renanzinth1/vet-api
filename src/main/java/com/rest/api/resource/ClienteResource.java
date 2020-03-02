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

import com.rest.api.model.Animal;
import com.rest.api.model.Cliente;
import com.rest.api.repository.IAnimal;
import com.rest.api.repository.ICliente;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {

	@Autowired
	private ICliente clientes;

	@Autowired
	private IAnimal animais;

	@GetMapping
	public ResponseEntity<List<Cliente>> listar() {

		List<Cliente> listaCliente = clientes.findAllByOrderByNomeAsc();

		return ResponseEntity.ok(listaCliente);
	}

	@GetMapping(value = "/{codigo}")
	public ResponseEntity<Cliente> buscar(@PathVariable("codigo") Long codigo) {

		Optional<Cliente> cliente = clientes.findById(codigo);

		if (cliente.isPresent())
			return ResponseEntity.ok(cliente.get());

		return ResponseEntity.notFound().build();
	}

	@GetMapping(value = "/{codigo}/animais")
	public ResponseEntity<List<Animal>> buscarAnimais(@PathVariable("codigo") Long codigo) {

		if (clientes.existsById(codigo)) {
			Optional<Cliente> cliente = clientes.findById(codigo);

			return ResponseEntity.ok(cliente.get().getListaAnimais());
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(value = "/{codigo}/animais/{codigoAnimal}")
	public ResponseEntity<Animal> buscarAnimal(@PathVariable("codigo") Long codigo,
			@PathVariable("codigoAnimal") Long codigoAnimal) {

		if (clientes.existsById(codigo)) {
			Optional<Cliente> cliente = clientes.findById(codigo);

			Optional<Animal> animal = animais.findById(codigoAnimal);

			if (animal.isPresent()) {
				if (cliente.get().getListaAnimais().contains(animal.get())) {
					return ResponseEntity.ok(animal.get());
				}
			}
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(value = "/cpf/{cpf}")
	public ResponseEntity<Cliente> buscarPorCpf(@PathVariable("cpf") String cpf) {

		Optional<Cliente> cliente = clientes.findByCpf(cpf);

		if (cliente.isPresent())
			return ResponseEntity.ok(cliente.get());

		return ResponseEntity.notFound().build();
	}

	@GetMapping(value = "/nome/{nome}")
	public ResponseEntity<List<Cliente>> buscarPorNome(@PathVariable("nome") String nome) {

		List<Cliente> listaCliente = clientes.findAllByNomeContainingIgnoreCaseOrderByNomeAsc(nome);

		if (listaCliente.isEmpty())
			return ResponseEntity.noContent().build();

		return ResponseEntity.ok(listaCliente);
	}

	@PostMapping
	public ResponseEntity<Void> salvar(@RequestBody Cliente cliente) {

		if (clientes.existsByCpf(cliente.getCpf()))
			return ResponseEntity.badRequest().build();

		if (cliente.getCpf().length() == 11) {
			cliente = clientes.save(cliente);

			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}")
					.buildAndExpand(cliente.getCodigo()).toUri();

			return ResponseEntity.created(uri).build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping(value = "/{codigo}")
	public ResponseEntity<Cliente> editar(@PathVariable("codigo") Long codigo, @RequestBody Cliente cliente) {
		
		if (clientes.existsById(codigo)) {
			Optional<Cliente> client = clientes.findByCpf(cliente.getCpf());
			if (client.isPresent()) {
				if (client.get().getCpf().equals(cliente.getCpf()) && client.get().getCodigo().equals(codigo)) {
					if (cliente.getCpf().length() == 11) {
						cliente.setCodigo(codigo);
						return ResponseEntity.accepted().body(clientes.save(cliente));
					} else {
						return ResponseEntity.badRequest().build();
					}
				} else {
					return ResponseEntity.badRequest().build();
				}
			}
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping(value = "/{codigo}")
	public ResponseEntity<Void> excluir(@PathVariable("codigo") Long codigo) {

		if (clientes.existsById(codigo)) {
			clientes.deleteById(codigo);

			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

}