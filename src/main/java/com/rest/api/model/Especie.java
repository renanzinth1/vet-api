package com.rest.api.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity(name = "Especies")
public class Especie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CODIGO_ESPECIE")
	@SequenceGenerator(name = "CODIGO_ESPECIE", sequenceName = "SEQ_CODIGO_ESPECIE", allocationSize = 1)
	private Long codigo;
	
	@Column(unique = true, nullable = false)
	private String nome;
	
	@OneToMany(mappedBy = "especie", targetEntity = SubEspecie.class, fetch = FetchType.LAZY)
	private List<SubEspecie> listaSubEspecies;

	public Especie() {
		super();
	}

	public Especie(Long codigo, String nome) {
		super();
		this.codigo = codigo;
		this.nome = nome;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}