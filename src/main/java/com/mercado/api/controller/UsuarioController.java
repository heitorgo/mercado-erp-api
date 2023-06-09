package com.mercado.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mercado.api.assembler.usuario.UsuarioInputDisassember;
import com.mercado.api.assembler.usuario.UsuarioModelAssembler;
import com.mercado.api.model.input.usuario.SenhaInput;
import com.mercado.api.model.input.usuario.UsuarioComSenhaInput;
import com.mercado.api.model.input.usuario.UsuarioInput;
import com.mercado.api.model.usuario.UsuarioModel;
import com.mercado.domain.exception.UsuarioNaoEncontradoException;
import com.mercado.domain.model.Usuario;
import com.mercado.domain.repository.UsuarioRepository;
import com.mercado.domain.service.UsuarioService;

@RequestMapping("/usuarios")
@RestController
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;
	
	@Autowired
	private UsuarioInputDisassember usuarioInputDisassember;

	@GetMapping
	public List<UsuarioModel> listar() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		return usuarioModelAssembler.toCollectionModel(usuarios);
	}

	@GetMapping("/{id}")
	public UsuarioModel buscar(@PathVariable Long id) {
		Usuario usuario = usuarioService.buscarOuFalhar(id);
		return usuarioModelAssembler.toModel(usuario);
	}

	@GetMapping("/nome")
	public List<UsuarioModel> listarPorNome(String nome) {
		List<Usuario> usuarios = usuarioRepository.findAllByNomeContaining(nome);
		if (usuarios.isEmpty()) {
			throw new UsuarioNaoEncontradoException("Nenhum usuario contém o nome %s");
		}
		return usuarioModelAssembler.toCollectionModel(usuarios);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioModel adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioComSenhaInput) {
		Usuario usuario = usuarioInputDisassember.toDomainModel(usuarioComSenhaInput);
		usuario = usuarioService.salvar(usuario);
		return usuarioModelAssembler.toModel(usuario);
	}

	@PutMapping("/{id}")
	public UsuarioModel atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioInput usuarioInput) {
		Usuario usuarioAtual = usuarioService.buscarOuFalhar(id);
		usuarioInputDisassember.copyToDomainObject(usuarioInput, usuarioAtual);
		usuarioAtual = usuarioService.salvar(usuarioAtual);
		return usuarioModelAssembler.toModel(usuarioAtual);
	}
	
	@PutMapping("/{id}/senha")
	public void atualizarSenha(@PathVariable Long id, @RequestBody @Valid SenhaInput senhaInput) {
		usuarioService.alterarSenha(id, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		usuarioService.excluir(id);
	}
	
	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long id) {
		usuarioService.ativar(id);
	}
	
	@DeleteMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long id) {
		usuarioService.inativar(id);
	}

}
