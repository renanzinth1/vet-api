package com.rest.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.api.model.Especie;

public interface IEspecie extends JpaRepository<Especie, Long> {

	public List<Especie> findAllByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);

	public boolean existsByNome(String nome);
	
	public List<Especie> findAllByOrderByNomeAsc();

}
