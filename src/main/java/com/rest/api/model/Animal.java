package com.rest.api.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "Animais")
public class Animal {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CODIGO_ANIMAL")
	@SequenceGenerator(name = "CODIGO_ANIMAL", sequenceName = "SEQ_CODIGO_ANIMAL", allocationSize = 1)
	private Long codigo;
	
	@Column(nullable = false)
	private String nome;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_nascimento")
	private Calendar dataNascimento;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private SexoAnimal sexo;

	public Animal() {
		super();
	}
	
	public Animal(Long codigo, String nome, Calendar dataNascimento, SexoAnimal sexo) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.sexo = sexo;
	}
	
	public Long getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Calendar getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Calendar dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public SexoAnimal getSexo() {
		return sexo;
	}

	public void setSexo(SexoAnimal sexo) {
		this.sexo = sexo;
	}
}
