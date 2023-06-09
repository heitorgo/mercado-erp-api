package com.mercado.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mercado.domain.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	List<Usuario> findAllByNomeContaining(String nome);
	
	Optional<Usuario> findByEmail(String email);

}
