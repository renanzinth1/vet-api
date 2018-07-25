package com.rest.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.validator.constraints.Length;

@Entity(name = "Clientes")
@PrimaryKeyJoinColumn(name = "codigo_pessoa")
public class Cliente extends Pessoa {

	@Column(unique = true, nullable = false)
	@Length(min = 11, max = 11)
	private String cpf;

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
