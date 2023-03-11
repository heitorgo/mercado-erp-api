package com.mercado.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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
	
	@GetMapping
	public List<Funcionario> listar(){
		return funcionarioRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Funcionario buscar(@PathVariable Long id){
		return funcionarioService.buscarOuFalhar(id);
	}
	
	@GetMapping("/nome")
	public ResponseEntity<?> listarPorNome(String nome){
		List<Funcionario> funcionarios = funcionarioRepository.findAllByNomeContaining(nome);
		if(funcionarios.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(funcionarios);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Funcionario adicionar(@RequestBody @Valid Funcionario funcionario){
		return funcionarioService.salvar(funcionario);
	}
	
	@PutMapping("/{id}")
	public Funcionario atualizar(@PathVariable Long id, @RequestBody @Valid Funcionario funcionario){
		Funcionario funcionarioAtual = funcionarioService.buscarOuFalhar(id);
		BeanUtils.copyProperties(funcionario, funcionarioAtual, "id", "dataCadastro");
		return funcionarioService.salvar(funcionarioAtual);
	}
	
	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id){
		funcionarioService.excluir(id);
	}

}
