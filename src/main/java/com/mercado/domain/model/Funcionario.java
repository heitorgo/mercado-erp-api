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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
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
public class Funcionario {
	
	@NotNull(groups = Groups.FuncionarioId.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@NotBlank
	@Column(nullable = false)
	private String nome;
	
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
	
	@JsonIgnore
	@Column(nullable = false)
	private boolean ativo=true;
	
	@ConvertGroup(from=Default.class, to=Groups.CargoId.class)
	@Valid
	@NotNull
	@ManyToOne
	@JoinColumn(nullable = false)
	private Cargo cargo;
	
	@JsonIgnore
	@OneToMany(mappedBy = "funcionario")
	private List<Venda> vendas = new ArrayList<>();
}
