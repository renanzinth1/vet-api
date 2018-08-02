package com.rest.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "Subespecies")
public class SubEspecie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CODIGO_SUBESPECIE")
	@SequenceGenerator(name = "CODIGO_SUBESPECIE", sequenceName = "SEQ_CODIGO_SUBESPECIE", allocationSize = 1)
	private Long codigo;
	
	@Column(unique = true, nullable = false)
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "codigo_especie")
	private Especie especie;

	public SubEspecie() {
		super();
	}
	
	public SubEspecie(Long codigo, String nome, Especie especie) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.especie = especie;
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

	public Especie getEspecie() {
		return especie;
	}

	public void setEspecie(Especie especie) {
		this.especie = especie;
	}
}
