package com.rest.api.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity(name = "Consultas")
public class Consulta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CODIGO_CONSULTA")
	@SequenceGenerator(name = "CODIGO_CONSULTA", sequenceName = "SEQ_CODIGO_CONSULTA", allocationSize = 1)
	private Long codigo;
	
	@Column
	private LocalDateTime dataHora;
	
	@Column(nullable = false)
	@Length(min = 15, max = 300)
	private String resumo;
	
	@ManyToOne
	@JoinColumn(name = "codigo_veterinario")
	private Veterinario veterinario;
	
	@ManyToOne
	@JoinColumn(name = "codigo_animal")
	private Animal animal;
	
	@JsonIgnoreProperties("consulta")
	@JsonProperty(access = Access.WRITE_ONLY)
	@OneToMany(mappedBy = "consulta", targetEntity = Tratamento.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Tratamento> listaTratamentos;

	public Consulta() {
		super();
	}

	public Consulta(Long codigo, LocalDateTime dataHora, @Length(min = 15, max = 300) String resumo,
			Veterinario veterinario, Animal animal, List<Tratamento> listaTratamentos) {
		super();
		this.codigo = codigo;
		this.dataHora = dataHora;
		this.resumo = resumo;
		this.veterinario = veterinario;
		this.animal = animal;
		this.listaTratamentos = listaTratamentos;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public String getResumo() {
		return resumo;
	}

	public void setResumo(String resumo) {
		this.resumo = resumo;
	}

	public Veterinario getVeterinario() {
		return veterinario;
	}

	public void setVeterinario(Veterinario veterinario) {
		this.veterinario = veterinario;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public List<Tratamento> getListaTratamentos() {
		return listaTratamentos;
	}

	public void setListaTratamentos(List<Tratamento> listaTratamentos) {
		this.listaTratamentos = listaTratamentos;
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
		Consulta other = (Consulta) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
}
