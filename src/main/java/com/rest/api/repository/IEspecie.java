package com.rest.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import com.rest.api.model.Especie;

public interface IEspecie extends JpaRepository<Especie, Long> {

	public List<Especie> findAllByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);

	public boolean existsByNome(String nome);

}
