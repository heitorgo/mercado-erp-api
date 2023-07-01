package com.mercado.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mercado.domain.model.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

	List<Empresa> findAllByNomeContaining(String nome);
	
	Optional<Empresa> findByRazaoSocial(String razaoSocial);
}
