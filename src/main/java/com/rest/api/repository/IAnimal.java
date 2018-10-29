package com.rest.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.api.model.Animal;

public interface IAnimal extends JpaRepository<Animal, Long> {

	public List<Animal> findAllByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);
	
	public List<Animal> findAllByOrderByCodigoAsc();
	
	public List<Animal> findAllByOrderByNomeAsc();

}
