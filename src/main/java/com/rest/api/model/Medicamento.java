package com.rest.api.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity(name = "Medicamentos")
public class Medicamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CODIGO_MEDICAMENTO")
	@SequenceGenerator(name = "CODIGO_MEDICAMENTO", sequenceName = "SEQ_CODIGO_MEDICAMENTO", allocationSize = 1)
	private Long codigo;
	
	@Column(unique = true, nullable = false)
	private String nome;
	
	@JsonIgnoreProperties("medicamento")
	@JsonProperty(access = Access.WRITE_ONLY)
	@OneToMany(mappedBy = "medicamento", targetEntity = Medicacao.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Medicacao> listaMedicacoes;

	public Medicamento() {
		super();
	}

	public Medicamento(Long codigo, String nome, List<Medicacao> listaMedicacoes) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.listaMedicacoes = listaMedicacoes;
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

	public List<Medicacao> getListaMedicacoes() {
		return listaMedicacoes;
	}

	public void setListaMedicacoes(List<Medicacao> listaMedicacoes) {
		this.listaMedicacoes = listaMedicacoes;
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
		Medicamento other = (Medicamento) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
