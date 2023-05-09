package com.mercado.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mercado.domain.model.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
	
	@Query("from Empresa e join fetch e.usuario")
	List<Empresa> findAll();

	List<Empresa> findAllByNomeContaining(String nome);
}
