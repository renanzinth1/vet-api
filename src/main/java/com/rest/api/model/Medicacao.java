package com.rest.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "Medicacoes")
public class Medicacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CODIGO_MEDICACAO")
	@SequenceGenerator(name = "CODIGO_MEDICACAO", sequenceName = "SEQ_CODIGO_MEDICACAO", allocationSize = 1)
	private Long codigo;
	
	@ManyToOne
	@JoinColumn(name = "codigo_tratamento")
	private Tratamento tratamento;
	
	@ManyToOne
	@JoinColumn(name = "codigo_medicamento")
	private Medicamento medicamento;
	
	@Column(nullable = true, columnDefinition = "NUMERIC(6,1)")
	private float dosagem;

	public Medicacao() {
		super();
	}

	public Medicacao(Long codigo, float dosagem, Tratamento tratamento, Medicamento medicamento) {
		super();
		this.codigo = codigo;
		this.dosagem = dosagem;
		this.tratamento = tratamento;
		this.medicamento = medicamento;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public float getDosagem() {
		return dosagem;
	}

	public void setDosagem(float dosagem) {
		this.dosagem = dosagem;
	}

	public Tratamento getTratamento() {
		return tratamento;
	}

	public void setTratamento(Tratamento tratamento) {
		this.tratamento = tratamento;
	}

	public Medicamento getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
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
		Medicacao other = (Medicacao) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
