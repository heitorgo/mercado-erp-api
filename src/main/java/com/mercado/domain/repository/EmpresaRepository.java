package com.mercado.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mercado.domain.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long>{

}
