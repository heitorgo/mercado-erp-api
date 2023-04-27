package com.mercado.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mercado.domain.exception.EmpresaNaoEncontradaException;
import com.mercado.domain.exception.EntidadeEmUsoException;
import com.mercado.domain.model.Empresa;
import com.mercado.domain.model.Usuario;
import com.mercado.domain.repository.EmpresaRepository;

@Service
public class EmpresaService {

	@Autowired
	private EmpresaRepository empresaRepository;
	@Autowired
	private UsuarioService usuarioService;

	private static final String msg_empresa_em_uso = "Empresa de codigo identificador %d está em uso";

	@Transactional
	public Empresa salvar(Empresa empresa) {
		Long usuarioId = empresa.getUsuario().getId();
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
		empresa.setUsuario(usuario);
		return empresaRepository.save(empresa);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			empresaRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EmpresaNaoEncontradaException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(msg_empresa_em_uso, id));
		}
	}

	public Empresa buscarOuFalhar(Long id) {
		return empresaRepository.findById(id).orElseThrow(() -> new EmpresaNaoEncontradaException(id));
	}

}
