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

import com.mercado.api.assembler.funcionario.FuncionarioInputDisassembler;
import com.mercado.api.assembler.funcionario.FuncionarioModelAssembler;
import com.mercado.api.model.FuncionarioModel;
import com.mercado.api.model.input.funcionario.FuncionarioAlteracaoInput;
import com.mercado.api.model.input.funcionario.FuncionarioInput;
import com.mercado.domain.exception.CargoNaoEncontradoException;
import com.mercado.domain.exception.NegocioException;
import com.mercado.domain.model.Funcionario;
import com.mercado.domain.repository.FuncionarioRepository;
import com.mercado.domain.service.FuncionarioService;

@RequestMapping("/funcionarios")
@RestController
public class FuncionarioController {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private FuncionarioService funcionarioService;
	
	@Autowired
	private FuncionarioModelAssembler funcionarioModelAssembler;
	
	@Autowired
	private FuncionarioInputDisassembler funcionarioInputDisassembler;

	@GetMapping
	public List<FuncionarioModel> listar() {
		List<Funcionario> funcionarios = funcionarioRepository.findAll();
		return funcionarioModelAssembler.toCollectionModel(funcionarios);
	}

	@GetMapping("/{id}")
	public FuncionarioModel buscar(@PathVariable Long id) {
		return funcionarioModelAssembler.toModel(funcionarioService.buscarOuFalhar(id));
	}

	@GetMapping("/nome")
	public ResponseEntity<?> listarPorNome(String nome) {
		List<Funcionario> funcionarios = funcionarioRepository.findAllByNomeContaining(nome);
		if (funcionarios.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(funcionarioModelAssembler.toCollectionModel(funcionarios));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FuncionarioModel adicionar(@RequestBody @Valid FuncionarioInput funcionarioInput) {
		try {
			Funcionario funcionario = funcionarioInputDisassembler.toDomainModel(funcionarioInput);
			return funcionarioModelAssembler.toModel(funcionarioService.salvar(funcionario));
		} catch (CargoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{id}")
	public FuncionarioModel atualizar(@PathVariable Long id, @RequestBody @Valid FuncionarioAlteracaoInput funcionarioAlteracaoInput) {
		try {
			Funcionario funcionarioAtual = funcionarioService.buscarOuFalhar(id);
			funcionarioInputDisassembler.copyToDomainObject(funcionarioAlteracaoInput, funcionarioAtual);
			return funcionarioModelAssembler.toModel(funcionarioService.salvar(funcionarioAtual));
		} catch (CargoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id) {
		funcionarioService.excluir(id);
	}

}
