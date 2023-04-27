package com.mercado;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.mercado.domain.model.Caixa;
import com.mercado.domain.model.Empresa;
import com.mercado.domain.model.Loja;
import com.mercado.domain.model.Usuario;
import com.mercado.domain.service.CaixaService;
import com.mercado.domain.service.EmpresaService;
import com.mercado.domain.service.LojaService;
import com.mercado.domain.service.UsuarioService;
import com.mercado.util.DatabaseCleaner;
import com.mercado.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class TesteCaixaIT {

	@LocalServerPort
	private int port;
	private Caixa caixa1;
	private String jsonCaixaCorreto;
	private String jsonCaixaIncorretoSemNome;
	private String jsonCaixaIncorretoSemSaldo;
	private String jsonCaixaIncorretoSaldo;
	private String jsonCaixaIncorretoSemLoja;
	private String jsonCaixaIncorretoLojaInexistente;
	private static final Long idCaixaInexistente = 100L;
	private static final String DADOS_INVALIDOS_PROBLEM_TYPE = "Dados inválidos";
	private static final String VIOLACAO_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";
	@Autowired
	private CaixaService caixaService;
	@Autowired
	private LojaService lojaService;
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private DatabaseCleaner databaseCleaner;

	@BeforeEach
	public void SetUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/caixas";

		databaseCleaner.clearTables();
		prepararDados();
		jsonCaixaCorreto = ResourceUtils.getContentFromResource("/json/correto/caixa-correto.json");
		jsonCaixaIncorretoSemNome = ResourceUtils
				.getContentFromResource("/json/incorreto/caixa/caixa-incorreto-sem-nome.json");
		jsonCaixaIncorretoSemSaldo = ResourceUtils
				.getContentFromResource("/json/incorreto/caixa/caixa-incorreto-sem-saldo.json");
		jsonCaixaIncorretoSaldo = ResourceUtils
				.getContentFromResource("/json/incorreto/caixa/caixa-incorreto-saldo.json");
		jsonCaixaIncorretoSemLoja = ResourceUtils
				.getContentFromResource("/json/incorreto/caixa/caixa-incorreto-sem-loja.json");
		jsonCaixaIncorretoLojaInexistente = ResourceUtils
				.getContentFromResource("/json/incorreto/caixa/caixa-incorreto-loja-inexistente.json");
	}

	private void prepararDados() {
		Usuario usuario = new Usuario();
		usuario.setNome("José Teixeira");
		usuario.setEmail("teixeiraze@hotmail.com");
		usuario.setSenha("Teixeira&2020*03");
		usuarioService.salvar(usuario);
		
		Empresa empresaAutoPecas = new Empresa();
		empresaAutoPecas.setNome("Auto Peças Itu");
		empresaAutoPecas.setRazaoSocial("Auto Peças Itu LTDA");
		empresaAutoPecas.setUsuario(usuario);
		empresaService.salvar(empresaAutoPecas);

		Loja loja1 = new Loja();
		loja1.setNome("Loja1");
		loja1.setEmpresa(empresaAutoPecas);
		lojaService.salvar(loja1);

		caixa1 = new Caixa();
		caixa1.setNome("Caixa1");
		caixa1.setSaldo(new BigDecimal(0));
		caixa1.setLoja(loja1);
		caixaService.salvar(caixa1);

	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCaixas() {
		given().accept(ContentType.JSON).when().get().then().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCaixaExistente() {
		given().accept(ContentType.JSON).pathParam("id", caixa1.getId()).when().get("/{id}").then()
				.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus404_QuandoConsultarCaixaInexistente() {
		given().accept(ContentType.JSON).pathParam("id", idCaixaInexistente).when().get("/{id}").then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarCaixaCorreto() {
		given().body(jsonCaixaCorreto).accept(ContentType.JSON).contentType(ContentType.JSON).when().post().then()
				.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarCaixaSemNome() {
		given().body(jsonCaixaIncorretoSemNome).accept(ContentType.JSON).contentType(ContentType.JSON).when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value()).body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarCaixaSemSaldo() {
		given().body(jsonCaixaIncorretoSemSaldo).accept(ContentType.JSON).contentType(ContentType.JSON).when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value()).body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarCaixaSaldoincorreta() {
		given().body(jsonCaixaIncorretoSaldo).accept(ContentType.JSON).contentType(ContentType.JSON).when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value()).body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarCaixaSemLoja() {
		given().body(jsonCaixaIncorretoSemLoja).accept(ContentType.JSON).contentType(ContentType.JSON).when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value()).body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarCaixaLojaInexistente() {
		given().body(jsonCaixaIncorretoLojaInexistente).accept(ContentType.JSON).contentType(ContentType.JSON).when()
				.post().then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(VIOLACAO_NEGOCIO_PROBLEM_TYPE));
	}

}
