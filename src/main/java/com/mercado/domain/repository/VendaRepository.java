package com.mercado.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mercado.domain.model.Venda;

@Repository
public interface VendaRepository  extends JpaRepository<Venda, Long>, JpaSpecificationExecutor<Venda>{
	
	@Query("from Venda v join fetch v.caixa join fetch v.funcionario left join fetch v.formasPagamento")
	List<Venda> findAll();
	
	List<Venda> findAllByDescricaoContaining(String descricao);

}
