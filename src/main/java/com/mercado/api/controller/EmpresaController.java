package com.mercado.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mercado.api.assembler.empresa.EmpresaInputDisassember;
import com.mercado.api.assembler.empresa.EmpresaModelAssembler;
import com.mercado.api.model.EmpresaModel;
import com.mercado.api.model.input.empresa.EmpresaAlteracaoInput;
import com.mercado.api.model.input.empresa.EmpresaInput;
import com.mercado.domain.exception.NegocioException;
import com.mercado.domain.exception.UsuarioNaoEncontradoException;
import com.mercado.domain.model.Empresa;
import com.mercado.domain.repository.EmpresaRepository;
import com.mercado.domain.service.EmpresaService;

@RequestMapping("/empresas")
@RestController
public class EmpresaController {

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private EmpresaModelAssembler empresaModelAssembler;
	
	@Autowired
	private EmpresaInputDisassember empresaInputDisassember;

	@GetMapping
	public List<EmpresaModel> listar() {
		List<Empresa> empresas = empresaRepository.findAll();
		return empresaModelAssembler.toCollectionModel(empresas);
	}

	@GetMapping("/{id}")
	public EmpresaModel buscar(@PathVariable Long id) {
		return empresaModelAssembler.toModel(empresaService.buscarOuFalhar(id));
	}

	@GetMapping("/nome")
	public ResponseEntity<?> listarPorNome(String nome) {
		List<Empresa> empresas = empresaRepository.findAllByNomeContaining(nome);
		if (empresas.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(empresaModelAssembler.toCollectionModel(empresas));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EmpresaModel adicionar(@RequestBody @Valid EmpresaInput empresaInput) {
		try {
			Empresa empresa = empresaInputDisassember.toDomainModel(empresaInput);
			return empresaModelAssembler.toModel(empresaService.salvar(empresa));
		}catch(UsuarioNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{id}")
	public EmpresaModel atualizar(@PathVariable Long id, @RequestBody @Valid EmpresaAlteracaoInput empresaAlteracaoInput) {
		try {
			Empresa empresaAtual = empresaService.buscarOuFalhar(id);
			empresaInputDisassember.copyToDomainObject(empresaAlteracaoInput, empresaAtual);
			return empresaModelAssembler.toModel(empresaService.salvar(empresaAtual));
		}catch(UsuarioNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id) {
		empresaService.excluir(id);
	}

}
