package com.rest.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.api.model.Medicacao;

public interface IMedicacao extends JpaRepository<Medicacao, Long> {
	
	public List<Medicacao> findAllByOrderByCodigoAsc();

}
