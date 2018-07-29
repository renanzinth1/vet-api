package com.rest.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.api.model.Veterinario;

public interface IVeterinario extends JpaRepository<Veterinario, Long> {

	public Optional<Veterinario> findByCfmv(String cfmv);

	public boolean existsByCfmv(String cfmv);

	public List<Veterinario> findAllByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);

}
