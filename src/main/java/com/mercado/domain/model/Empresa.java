package com.mercado.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mercado.core.validation.Groups;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Empresa {
	
	@NotNull(groups = Groups.EmpresaId.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@NotBlank
	@Column(nullable = false)
	private String nome;
	
	@NotBlank
	@Column(nullable = false)
	private String razaoSocial;
	
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
	@NotNull
	@Column(nullable = false)
	private boolean ativo=true;
	
	@JsonIgnore
	@OneToMany(mappedBy = "empresa")
	private List<Loja> lojas = new ArrayList<>();

}
