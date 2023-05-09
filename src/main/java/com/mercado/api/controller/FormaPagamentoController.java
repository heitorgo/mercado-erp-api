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

import com.mercado.api.assembler.formaPagamento.FormaPagamentoInputDisassember;
import com.mercado.api.assembler.formaPagamento.FormaPagamentoModelAssembler;
import com.mercado.api.model.FormaPagamentoModel;
import com.mercado.api.model.input.formaPagamento.FormaPagamentoAlteracaoInput;
import com.mercado.api.model.input.formaPagamento.FormaPagamentoInput;
import com.mercado.domain.model.FormaPagamento;
import com.mercado.domain.repository.FormaPagamentoRepository;
import com.mercado.domain.service.FormaPagamentoService;

@RequestMapping("/formas-pagamento")
@RestController
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;

	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
	
	@Autowired
	private FormaPagamentoInputDisassember formaPagamentoInputDisassembler;

	@GetMapping
	public List<FormaPagamentoModel> listar() {
		List<FormaPagamento> formasPagamento = formaPagamentoRepository.findAll();
		return formaPagamentoModelAssembler.toCollectionModel(formasPagamento);
	}

	@GetMapping("/{id}")
	public FormaPagamentoModel buscar(@PathVariable Long id) {
		return formaPagamentoModelAssembler.toModel(formaPagamentoService.buscarOuFalhar(id));
	}

	@GetMapping("/titulo")
	public ResponseEntity<?> listarPorTitulo(String titulo) {
		List<FormaPagamento> formasPagamento = formaPagamentoRepository.findAllByTituloContaining(titulo);
		if (formasPagamento.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(formaPagamentoModelAssembler.toCollectionModel(formasPagamento));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainModel(formaPagamentoInput);
		return formaPagamentoModelAssembler.toModel(formaPagamentoService.salvar(formaPagamento));
	}

	@PutMapping("/{id}")
	public FormaPagamentoModel atualizar(@PathVariable Long id, @RequestBody @Valid FormaPagamentoAlteracaoInput formaPagamentoAlteracaoInput) {
		FormaPagamento formaPagamentoAtual = formaPagamentoService.buscarOuFalhar(id);
		formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoAlteracaoInput, formaPagamentoAtual);
		return formaPagamentoModelAssembler.toModel(formaPagamentoService.salvar(formaPagamentoAtual));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		formaPagamentoService.excluir(id);
	}

}
