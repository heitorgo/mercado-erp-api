package com.mercado.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mercado.domain.model.Caixa;

@Repository
public interface CaixaRepository  extends JpaRepository<Caixa, Long>, JpaSpecificationExecutor<Caixa>{
	
	@Query("from Caixa c join fetch c.loja")
	List<Caixa> findAll();

}
