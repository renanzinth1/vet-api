package com.rest.api.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.validator.constraints.Length;

@Entity(name = "Clientes")
@PrimaryKeyJoinColumn(name = "codigo_pessoa")
public class Cliente extends Pessoa {

	@Column(unique = true, nullable = false)
	@Length(min = 11, max = 11)
	private String cpf;
	
	@OneToMany(mappedBy = "cliente", targetEntity = Animal.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Animal> listaAnimais;
	
	public Cliente() {
		super();
	}

	public Cliente(String cpf) {
		super();
		this.cpf = cpf;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
}
