package com.rest.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.api.model.Tratamento;

public interface ITratamento extends JpaRepository<Tratamento, Long> {
	
	public List<Tratamento> findAllByOrderByCodigoAsc();

}
