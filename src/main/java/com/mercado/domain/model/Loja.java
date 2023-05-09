package com.mercado.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Loja {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@Column(nullable = false)
	private String nome;

	private BigDecimal saldo;

	@CreationTimestamp
	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;

	@UpdateTimestamp
	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;

	@Column(nullable = false)
	private boolean ativo = true;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Empresa empresa;

	@OneToMany(mappedBy = "loja")
	private List<Cargo> cargos = new ArrayList<>();

	@OneToMany(mappedBy = "loja")
	private List<Caixa> caixas = new ArrayList<>();
	
	@OneToMany(mappedBy = "loja")
	private List<Produto> produtos = new ArrayList<>();

}
