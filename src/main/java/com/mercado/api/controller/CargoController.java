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
import org.springframework.web.bind.annotation.RestController;

import com.mercado.domain.exception.EntidadeEmUsoException;
import com.mercado.domain.exception.EntidadeNaoEncontradaException;
import com.mercado.domain.model.Cargo;
import com.mercado.domain.repository.CargoRepository;
import com.mercado.domain.service.CargoService;

@RequestMapping("/cargos")
@RestController
public class CargoController{
	
	@Autowired
	private CargoRepository cargoRepository;
	
	@Autowired
	private CargoService cargoService;
	
	@GetMapping
	public List<Cargo> listar(){
		return cargoRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cargo> buscar(@PathVariable Long id){
		Optional<Cargo> cargo = cargoRepository.findById(id);
		if(cargo.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(cargo.get());
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Cargo cargo) {
		try {
			cargoService.salvar(cargo);
			return ResponseEntity.status(HttpStatus.CREATED).body(cargo);
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar( @PathVariable Long id, @RequestBody Cargo cargo){
		try {
			Optional<Cargo> cargoAtual = cargoRepository.findById(id);
			if(cargoAtual.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			BeanUtils.copyProperties(cargo, cargoAtual.get(), "id", "dataCadastro");
			Cargo cargoSalvo = cargoService.salvar(cargoAtual.get());
			return ResponseEntity.ok(cargoSalvo);
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id){
		try {
			Optional<Cargo> cargo = cargoRepository.findById(id);
			if(cargo.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			cargoService.excluir(id);
			return ResponseEntity.noContent().build();
		}catch(EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

}
