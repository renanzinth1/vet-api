package com.rest.api.model;

import java.time.LocalDate;

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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity(name = "Animais")
public class Animal {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CODIGO_ANIMAL")
	@SequenceGenerator(name = "CODIGO_ANIMAL", sequenceName = "SEQ_CODIGO_ANIMAL", allocationSize = 1)
	private Long codigo;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(name = "data_nascimento")
	private LocalDate dataNascimento;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private SexoAnimal sexo;
	
	@ManyToOne
	@JoinColumn(name = "codigo_subEspecie")
	private SubEspecie subEspecie;
	
	@ManyToOne
	@JsonProperty(access = Access.WRITE_ONLY)
	@JoinColumn(name = "codigo_cliente")
	private Cliente cliente;

	public Animal() {
		super();
	}

	public Animal(Long codigo, String nome, LocalDate dataNascimento, SexoAnimal sexo, SubEspecie subEspecie,
			Cliente cliente) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.sexo = sexo;
		this.subEspecie = subEspecie;
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

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public SexoAnimal getSexo() {
		return sexo;
	}

	public void setSexo(SexoAnimal sexo) {
		this.sexo = sexo;
	}

	public SubEspecie getSubEspecie() {
		return subEspecie;
	}

	public void setSubEspecie(SubEspecie subEspecie) {
		this.subEspecie = subEspecie;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Animal other = (Animal) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
}
