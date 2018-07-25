package com.rest.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.api.model.Animal;

public interface IAnimais extends JpaRepository<Animal, Long> {

}
