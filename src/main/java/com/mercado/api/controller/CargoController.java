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

import com.mercado.domain.exception.CargoNaoEncontradoException;
import com.mercado.domain.exception.NegocioException;
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
	public Cargo buscar(@PathVariable Long id){
		return cargoService.buscarOuFalhar(id);
	}
	
	@GetMapping("/titulo")
	public ResponseEntity<?> listarPorTitulo(String titulo){
		List<Cargo> cargos = cargoRepository.findAllByTituloContaining(titulo);
		if(cargos.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(cargos);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cargo adicionar(@RequestBody @Valid Cargo cargo) {
		try {
			return cargoService.salvar(cargo);
		}catch(CargoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
		
	}
	
	@PutMapping("/{id}")
	public Cargo atualizar( @PathVariable Long id, @RequestBody @Valid Cargo cargo){
		Cargo cargoAtual = cargoService.buscarOuFalhar(id);
		BeanUtils.copyProperties(cargo, cargoAtual, "id", "dataCadastro");
		try {
			return cargoService.salvar(cargoAtual);
		}catch(CargoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id){
		cargoService.excluir(id);
	}

}
