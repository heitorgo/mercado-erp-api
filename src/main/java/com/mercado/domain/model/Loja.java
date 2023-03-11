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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class Loja {
	
	@NotNull(groups=Groups.LojaId.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@NotBlank
	@Column(nullable = false)
	private String nome;
	
	@PositiveOrZero
	@NotNull
	@Column(nullable = false)
	private BigDecimal saldo;
	
	@JsonIgnore
	@CreationTimestamp
	@Column(columnDefinition = "datetime")
	@PastOrPresent
	private LocalDateTime dataCadastro;
	
	@JsonIgnore
	@UpdateTimestamp
	@Column(columnDefinition = "datetime")
	@PastOrPresent
	private LocalDateTime dataAtualizacao;
	
	@JsonIgnore
	@Column(nullable = false)
	private boolean ativo=true;
	
	@ConvertGroup(from=Default.class, to=Groups.EmpresaId.class)
	@NotNull
	@Valid
	@ManyToOne
	@JoinColumn(nullable = false)
	private Empresa empresa;
	
	@OneToMany(mappedBy = "loja")
	@JsonIgnore
	private List<Cargo> cargos = new ArrayList<>();
	
	@OneToMany(mappedBy = "loja")
	@JsonIgnore
	private List<Caixa> caixas = new ArrayList<>();

}
