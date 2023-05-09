package com.mercado.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mercado.domain.exception.EntidadeEmUsoException;
import com.mercado.domain.exception.UsuarioNaoEncontradoException;
import com.mercado.domain.model.Usuario;
import com.mercado.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private static final String msg_usuario_em_uso = "O usuário de codigo identificador %d está em uso";
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	public void excluir(Long id) {
		try {
			usuarioRepository.deleteById(id);
			usuarioRepository.flush();
		}catch(EmptyResultDataAccessException e) {
			throw new UsuarioNaoEncontradoException(id);
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(msg_usuario_em_uso, id));
		}
	}
	
	public Usuario buscarOuFalhar(Long id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
	}

}
