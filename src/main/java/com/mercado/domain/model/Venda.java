package com.mercado.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Venda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@Column(nullable = false)
	private double valor;
	
	@Column(nullable = false)
	private String descricao;
	
	@JsonIgnore
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataCadastro;
	
	@JsonIgnore
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataAtualizacao;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Caixa caixa;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Funcionario funcionario;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name ="venda_forma_pagamento",
			joinColumns = @JoinColumn(name = "venda_id"),
			inverseJoinColumns = @JoinColumn(name="forma_pagamento_id"))
	private List<FormaPagamento> formasPagamento = new ArrayList<>();

}
