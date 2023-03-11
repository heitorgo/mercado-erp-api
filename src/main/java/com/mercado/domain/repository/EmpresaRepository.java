package com.mercado.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mercado.domain.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long>{
	
	List<Empresa> findAllByNomeContaining(String nome);
}
