package com.mercado.domain.model;

import java.math.BigDecimal;
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
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mercado.core.validation.Groups;

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
	
	@NotNull
	@PositiveOrZero
	@Column(nullable = false)
	private BigDecimal valor;
	
	@NotBlank
	@Column(nullable = false)
	private String descricao;
	
	@PastOrPresent
	@JsonIgnore
	@CreationTimestamp
	@Column(columnDefinition = "datetime")
	private LocalDateTime dataCadastro;
	
	@PastOrPresent
	@JsonIgnore
	@UpdateTimestamp
	@Column(columnDefinition = "datetime")
	private LocalDateTime dataAtualizacao;
	
	@ConvertGroup(from = Default.class, to = Groups.CaixaId.class)
	@Valid
	@NotNull
	@ManyToOne
	@JoinColumn(nullable = false)
	private Caixa caixa;
	
	@ConvertGroup(from = Default.class, to = Groups.FuncionarioId.class)
	@Valid
	@NotNull
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
