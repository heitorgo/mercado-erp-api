package com.mercado.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mercado.core.validation.Groups;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Produto {

	@NotNull(groups = Groups.ProdutoId.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String nome;
	
	@NotBlank
	@Column(nullable = false)
	private String descricao;
	
	@PositiveOrZero
	@NotNull
	@Column(nullable = false)
	private Integer quantidade;
	
	@Positive
	@NotNull
	@Column(nullable = false)
	private BigDecimal valor;

	@PastOrPresent
	@JsonIgnore
	@CreationTimestamp
	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;

	@PastOrPresent
	@JsonIgnore
	@UpdateTimestamp
	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;

	@JsonIgnore
	@Column(nullable = false)
	private boolean ativo = true;

	@JsonIgnoreProperties(value = { "nome", "empresa" })
	@ConvertGroup(from = Default.class, to = Groups.LojaId.class)
	@Valid
	@NotNull
	@ManyToOne
	@JoinColumn(nullable = false)
	private Loja loja;
	
}
