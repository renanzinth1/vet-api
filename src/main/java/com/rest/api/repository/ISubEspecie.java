package com.rest.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.api.model.SubEspecie;

public interface ISubEspecie extends JpaRepository<SubEspecie, Long> {

	public List<SubEspecie> findAllByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);

	public boolean existsByNome(String nome);
	
	public List<SubEspecie> findAllByOrderByCodigoAsc();

	public List<SubEspecie> findAllByOrderByNomeAsc();
	
}
