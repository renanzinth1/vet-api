package com.rest.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.api.model.Medicamento;

public interface IMedicamento extends JpaRepository<Medicamento, Long> {
	
	public Optional<Medicamento> findByNome(String nome);

	public boolean existsByNome(String nome);

	public List<Medicamento> findAllByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);
}
