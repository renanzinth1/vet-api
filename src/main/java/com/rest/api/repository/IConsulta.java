package com.rest.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.api.model.Consulta;

public interface IConsulta extends JpaRepository<Consulta, Long> {

}
