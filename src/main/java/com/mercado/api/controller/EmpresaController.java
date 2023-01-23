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
import com.mercado.domain.model.Empresa;
import com.mercado.domain.repository.EmpresaRepository;
import com.mercado.domain.service.EmpresaService;

@RequestMapping("/empresas")
@RestController
public class EmpresaController{
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private EmpresaService empresaService;
	
	@GetMapping
	public List<Empresa> listar(){
		return empresaRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Empresa> buscar(@PathVariable Long id){
		Optional<Empresa> empresa = empresaRepository.findById(id);
		if(empresa.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(empresa.get());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Empresa adicionar(@RequestBody Empresa empresa) {
		return empresaService.salvar(empresa);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar( @PathVariable Long id, @RequestBody Empresa empresa){
		Optional<Empresa> empresaAtual = empresaRepository.findById(id);
		if(empresaAtual.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		BeanUtils.copyProperties(empresa, empresaAtual.get(), "id", "dataCadastro");
		Empresa empresaSalva = empresaService.salvar(empresaAtual.get());
		return ResponseEntity.ok(empresaSalva);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id){
		try {
			Optional<Empresa> empresa = empresaRepository.findById(id);
			if(empresa.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			empresaService.excluir(id);
			return ResponseEntity.noContent().build();
		}catch(EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

}
