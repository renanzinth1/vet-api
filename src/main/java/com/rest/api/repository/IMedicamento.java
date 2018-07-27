package com.rest.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.api.model.Medicamento;

public interface IMedicamento extends JpaRepository<Medicamento, Long> {
	
	public Optional<Medicamento> findByNome(String nome);
}
