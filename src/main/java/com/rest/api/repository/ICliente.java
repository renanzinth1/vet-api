package com.rest.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.api.model.Cliente;

public interface ICliente extends JpaRepository<Cliente, Long> {
	
	public Optional<Cliente> findByCpf(String cpf);

	public boolean existsByCpf(String cpf);

	public List<Cliente> findAllByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);
	
	public List<Cliente> findAllByOrderByCodigoAsc();
	
	public List<Cliente> findAllByOrderByNomeAsc();
	
}