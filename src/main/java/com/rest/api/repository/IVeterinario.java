package com.rest.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.api.model.Veterinario;

public interface IVeterinario extends JpaRepository<Veterinario, Long> {

	public Optional<Veterinario> findByCfmv(String cfmv);

}
