package com.rest.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity(name = "Veterinarios")
@PrimaryKeyJoinColumn(name = "codigo_pessoa")
public class Veterinario extends Pessoa {

	@Column(unique = true, nullable = false)
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
