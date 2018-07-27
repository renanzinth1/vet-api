package com.rest.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rest.api.model.Cliente;

public interface IClientes extends JpaRepository<Cliente, Long> {
	
	//@Query(value = "SELECT * FROM Clientes c WHERE c.cpf = ?1", nativeQuery = true)
	public Optional<Cliente> findByCpf(String cpf);
}