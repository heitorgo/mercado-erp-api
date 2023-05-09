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

import com.mercado.api.assembler.cargo.CargoInputDisassembler;
import com.mercado.api.assembler.cargo.CargoModelAssembler;
import com.mercado.api.model.CargoModel;
import com.mercado.api.model.input.cargo.CargoAlteracaoInput;
import com.mercado.api.model.input.cargo.CargoInput;
import com.mercado.domain.exception.LojaNaoEncontradaException;
import com.mercado.domain.exception.NegocioException;
import com.mercado.domain.model.Cargo;
import com.mercado.domain.repository.CargoRepository;
import com.mercado.domain.service.CargoService;

@RequestMapping("/cargos")
@RestController
public class CargoController {

	@Autowired
	private CargoRepository cargoRepository;

	@Autowired
	private CargoService cargoService;
	
	@Autowired
	private CargoModelAssembler cargoModelAssembler;
	
	@Autowired
	private CargoInputDisassembler cargoInputDisassembler;

	@GetMapping
	public List<CargoModel> listar() {
		List<Cargo> cargos = cargoRepository.findAll();
		return cargoModelAssembler.toCollectionModel(cargos);
	}

	@GetMapping("/{id}")
	public CargoModel buscar(@PathVariable Long id) {
		return cargoModelAssembler.toModel(cargoService.buscarOuFalhar(id));
	}

	@GetMapping("/titulo")
	public ResponseEntity<?> listarPorTitulo(String titulo) {
		List<Cargo> cargos = cargoRepository.findAllByTituloContaining(titulo);
		if (cargos.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(cargoModelAssembler.toCollectionModel(cargos));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CargoModel adicionar(@RequestBody @Valid CargoInput cargoInput) {
		try {
			Cargo cargo = cargoInputDisassembler.toDomainModel(cargoInput);
			return cargoModelAssembler.toModel(cargoService.salvar(cargo));
		} catch (LojaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@PutMapping("/{id}")
	public CargoModel atualizar(@PathVariable Long id, @RequestBody @Valid CargoAlteracaoInput cargoAlteracaoInput) {
		try {
			Cargo cargoAtual = cargoService.buscarOuFalhar(id);
			cargoInputDisassembler.copyToDomainObject(cargoAlteracaoInput, cargoAtual);
			return cargoModelAssembler.toModel(cargoService.salvar(cargoAtual));
		} catch (LojaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cargoService.excluir(id);
	}

}
