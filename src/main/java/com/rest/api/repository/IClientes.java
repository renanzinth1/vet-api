package com.rest.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.api.model.Cliente;

public interface IClientes extends JpaRepository<Cliente, Long> {
	
	//@Query(value = "SELECT * FROM Clientes c WHERE c.cpf = ?1", nativeQuery = true)
	public Optional<Cliente> findByCpf(String cpf);

	public boolean existsByCpf(String cpf);

	public List<Cliente> findAllByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);
	
}