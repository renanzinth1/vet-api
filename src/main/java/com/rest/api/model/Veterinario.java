package com.rest.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.validator.constraints.Length;

@Entity(name = "Veterinarios")
@PrimaryKeyJoinColumn(name = "codigo_pessoa")
public class Veterinario extends Pessoa {

	@Column(unique = true, nullable = false)
	@Length(min = 4, max = 4)
	private String cfmv;

	public Veterinario() {
		super();
	}

	public Veterinario(String cfmv) {
		super();
		this.cfmv = cfmv;
	}

	public String getCfmv() {
		return cfmv;
	}

	public void setCfmv(String cfmv) {
		this.cfmv = cfmv;
	}
}
