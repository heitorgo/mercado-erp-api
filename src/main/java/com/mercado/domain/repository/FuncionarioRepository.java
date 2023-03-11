package com.mercado.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mercado.domain.model.Funcionario;
import com.mercado.domain.model.Loja;

@Repository
public interface FuncionarioRepository  extends JpaRepository<Funcionario, Long>, JpaSpecificationExecutor<Loja>{
	
	@Query("from Funcionario f left join fetch f.cargos")
	List<Funcionario> findAll();
	
	List<Funcionario> findAllByNomeContaining(String nome);

}
