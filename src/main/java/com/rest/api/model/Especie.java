package com.rest.api.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity(name = "Especies")
public class Especie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CODIGO_ESPECIE")
	@SequenceGenerator(name = "CODIGO_ESPECIE", sequenceName = "SEQ_CODIGO_ESPECIE", allocationSize = 1)
	private Long codigo;
	
	@Column(unique = true, nullable = false)
	private String nome;
	
	// Ignorar a lista de SubEspecie na hora da listagem
	@JsonProperty(access = Access.WRITE_ONLY)
	@OneToMany(mappedBy = "especie", targetEntity = SubEspecie.class, fetch = FetchType.LAZY)
	private List<SubEspecie> listaSubEspecies;

	public Especie() {
		super();
	}

	public Especie(Long codigo, String nome, List<SubEspecie> listaSubEspecies) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.listaSubEspecies = listaSubEspecies;
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

	public List<SubEspecie> getListaSubEspecies() {
		return listaSubEspecies;
	}

	public void setListaSubEspecies(List<SubEspecie> listaSubEspecies) {
		this.listaSubEspecies = listaSubEspecies;
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
		Especie other = (Especie) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
