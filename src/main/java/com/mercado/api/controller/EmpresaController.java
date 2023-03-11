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
	public Empresa buscar(@PathVariable Long id){
		return empresaService.buscarOuFalhar(id);
	}
	
	@GetMapping("/nome")
	public ResponseEntity<?> listarPorNome(String nome){
		List<Empresa> empresas = empresaRepository.findAllByNomeContaining(nome);
		if(empresas.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(empresas);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Empresa adicionar(@RequestBody @Valid Empresa empresa) {
		return empresaService.salvar(empresa);
	}
	
	@PutMapping("/{id}")
	public Empresa atualizar( @PathVariable Long id, @RequestBody @Valid Empresa empresa){
		Empresa empresaAtual = empresaService.buscarOuFalhar(id);
		BeanUtils.copyProperties(empresa, empresaAtual, "id", "dataCadastro");
		return empresaService.salvar(empresaAtual);
	}
	
	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id){
		empresaService.excluir(id);
	}

}
