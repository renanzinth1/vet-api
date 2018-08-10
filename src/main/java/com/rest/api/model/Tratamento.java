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

@Entity(name = "Tratamentos")
public class Tratamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CODIGO_TRATAMENTO")
	@SequenceGenerator(name = "CODIGO_TRATAMENTO", sequenceName = "SEQ_CODIGO_TRATAMENTO", allocationSize = 1)
	private Long codigo;
	
	@Column
	private String resumo;
	
	@JsonIgnoreProperties("tratamento")
	@OneToMany(mappedBy = "tratamento", targetEntity = Medicacao.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Medicacao> listaMedicacoes;

	public Tratamento() {
		super();
	}

	public Tratamento(Long codigo, String resumo, List<Medicacao> listaMedicacoes) {
		super();
		this.codigo = codigo;
		this.resumo = resumo;
		this.listaMedicacoes = listaMedicacoes;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getResumo() {
		return resumo;
	}

	public void setResumo(String resumo) {
		this.resumo = resumo;
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
		Tratamento other = (Tratamento) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
