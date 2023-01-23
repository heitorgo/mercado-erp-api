package com.mercado.api.controller;

import java.util.List;
import java.util.Optional;

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

import com.mercado.domain.exception.EntidadeEmUsoException;
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
	public ResponseEntity<Funcionario> buscar(@PathVariable Long id){
		Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
		if(funcionario.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(funcionario.get());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Funcionario adicionar(@RequestBody Funcionario funcionario){
		return funcionarioService.salvar(funcionario);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Funcionario funcionario){
		Optional<Funcionario> funcionarioAtual = funcionarioRepository.findById(id);
		if(funcionarioAtual.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		BeanUtils.copyProperties(funcionario, funcionarioAtual.get(), "id", "dataCadastro");
		Funcionario funcionarioSalvo = funcionarioService.salvar(funcionarioAtual.get());
		return ResponseEntity.ok(funcionarioSalvo);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id){
		try {
			Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
			if(funcionario.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			funcionarioService.excluir(id);
			return ResponseEntity.noContent().build();
		}catch(EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

}
