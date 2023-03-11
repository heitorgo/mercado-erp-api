package com.mercado.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mercado.domain.model.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Long>{
	
	@Query("from Cargo c join fetch c.loja")
	List<Cargo> findAll();
	
	List<Cargo> findAllByTituloContaining(String titulo);

}
