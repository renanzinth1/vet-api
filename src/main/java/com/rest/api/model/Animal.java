package com.rest.api.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name = "Animais")
public class Animal {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CODIGO_ANIMAL")
	@SequenceGenerator(name = "CODIGO_ANIMAL", sequenceName = "SEQ_CODIGO_ANIMAL", allocationSize = 1)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long codigo;
	
	@Column(nullable = false)
	private String nome;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_nascimento")
	private Calendar dataNascimento;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private SexoAnimal sexo;
	
	@ManyToOne
	@JoinColumn(name = "codigo_cliente")
	private Cliente cliente;

	public Animal() {
		super();
	}
	
	public Animal(Long codigo, String nome, Calendar dataNascimento, SexoAnimal sexo, Cliente cliente) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.sexo = sexo;
		this.cliente = cliente;
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
