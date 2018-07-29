package com.rest.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "Medicamentos")
public class Medicamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CODIGO_MEDICAMENTO")
	@SequenceGenerator(name = "CODIGO_MEDICAMENTO", sequenceName = "SEQ_CODIGO_MEDICAMENTO", allocationSize = 1)
	private Long codigo;
	
	@Column(unique = true, nullable = false)
	private String nome;

	public Medicamento() {
		super();
	}

	public Medicamento(Long codigo, String nome) {
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
