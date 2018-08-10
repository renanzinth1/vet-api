package com.rest.api.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity(name = "Clientes")
@PrimaryKeyJoinColumn(name = "codigo_pessoa")
public class Cliente extends Pessoa {

	@Column(unique = true, nullable = false)
	@Length(min = 11, max = 11)
	private String cpf;
	
	//Foi usado essa anotação para ignorar o atributo do cliente
	//quando for retornar os clientes e seus respectivos animais.
	@JsonIgnoreProperties("cliente")
	//// Ignorar a lista de Animais na hora da listagem
	@JsonProperty(access = Access.WRITE_ONLY)
	@OneToMany(mappedBy = "cliente", targetEntity = Animal.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Animal> listaAnimais;
	
	public Cliente() {
		super();
	}

	public Cliente(@Length(min = 11, max = 11) String cpf, List<Animal> listaAnimais) {
		super();
		this.cpf = cpf;
		this.listaAnimais = listaAnimais;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public List<Animal> getListaAnimais() {
		return listaAnimais;
	}

	public void setListaAnimais(List<Animal> listaAnimais) {
		this.listaAnimais = listaAnimais;
	}
	
}
