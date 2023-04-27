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
import com.mercado.domain.model.Cargo;
import com.mercado.domain.model.Empresa;
import com.mercado.domain.model.Funcionario;
import com.mercado.domain.model.Loja;
import com.mercado.domain.model.Usuario;
import com.mercado.domain.model.Venda;
import com.mercado.domain.service.CaixaService;
import com.mercado.domain.service.CargoService;
import com.mercado.domain.service.EmpresaService;
import com.mercado.domain.service.FuncionarioService;
import com.mercado.domain.service.LojaService;
import com.mercado.domain.service.UsuarioService;
import com.mercado.domain.service.VendaService;
import com.mercado.util.DatabaseCleaner;
import com.mercado.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class TesteVendaIT {

	@LocalServerPort
	private int port;
	private Venda venda1;
	private String jsonVendaCorreto;
	private String jsonVendaIncorretoSemValor;
	private String jsonVendaIncorretoValor;
	private String jsonVendaIncorretoSemDescricao;
	private String jsonVendaIncorretoSemCaixa;
	private String jsonVendaIncorretoCaixaInexistente;
	private String jsonVendaIncorretoSemFuncionario;
	private String jsonVendaIncorretoFuncionarioInexistente;
	private static final Long idVendaInexistente = 100L;
	private static final String DADOS_INVALIDOS_PROBLEM_TYPE = "Dados inválidos";
	private static final String VIOLACAO_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";
	@Autowired
	private VendaService vendaService;
	@Autowired
	private CaixaService caixaService;
	@Autowired
	private CargoService cargoService;
	@Autowired
	private FuncionarioService funcionarioService;
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private LojaService lojaService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private DatabaseCleaner databaseCleaner;

	@BeforeEach
	public void SetUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/vendas";

		databaseCleaner.clearTables();
		prepararDados();
		jsonVendaCorreto = ResourceUtils.getContentFromResource("/json/correto/venda-correto.json");
		jsonVendaIncorretoSemValor = ResourceUtils
				.getContentFromResource("/json/incorreto/venda/venda-incorreto-sem-valor.json");
		jsonVendaIncorretoValor = ResourceUtils
				.getContentFromResource("/json/incorreto/venda/venda-incorreto-valor.json");
		jsonVendaIncorretoSemDescricao = ResourceUtils
				.getContentFromResource("/json/incorreto/venda/venda-incorreto-sem-descricao.json");
		jsonVendaIncorretoSemCaixa = ResourceUtils
				.getContentFromResource("/json/incorreto/venda/venda-incorreto-sem-caixa.json");
		jsonVendaIncorretoCaixaInexistente = ResourceUtils
				.getContentFromResource("/json/incorreto/venda/venda-incorreto-caixa-inexistente.json");
		jsonVendaIncorretoSemFuncionario = ResourceUtils
				.getContentFromResource("/json/incorreto/venda/venda-incorreto-sem-funcionario.json");
		jsonVendaIncorretoFuncionarioInexistente = ResourceUtils
				.getContentFromResource("/json/incorreto/venda/venda-incorreto-funcionario-inexistente.json");
	}

	private void prepararDados() {
		Usuario usuario1 = new Usuario();
		usuario1.setNome("José Teixeira");
		usuario1.setEmail("teixeiraze@hotmail.com");
		usuario1.setSenha("Teixeira&2020*03");
		usuarioService.salvar(usuario1);
		
		Empresa empresaAutoPecas = new Empresa();
		empresaAutoPecas.setNome("Auto Peças Itu");
		empresaAutoPecas.setRazaoSocial("Auto Peças Itu LTDA");
		empresaAutoPecas.setUsuario(usuario1);
		empresaService.salvar(empresaAutoPecas);

		Loja loja1 = new Loja();
		loja1.setNome("Loja1");
		loja1.setEmpresa(empresaAutoPecas);
		lojaService.salvar(loja1);

		Caixa caixa1 = new Caixa();
		caixa1.setNome("Caixa1");
		caixa1.setSaldo(new BigDecimal(0));
		caixa1.setLoja(loja1);
		caixaService.salvar(caixa1);

		Cargo cargo1 = new Cargo();
		cargo1.setTitulo("Gerente");
		cargo1.setRemuneracao(new BigDecimal(7000));
		cargo1.setLoja(loja1);
		cargoService.salvar(cargo1);

		Funcionario funcionario1 = new Funcionario();
		funcionario1.setNome("Pedro");
		funcionario1.setCargo(cargo1);
		funcionarioService.salvar(funcionario1);

		venda1 = new Venda();
		venda1.setValor(new BigDecimal(1000));
		venda1.setDescricao("Venda de Produtos");
		venda1.setCaixa(caixa1);
		venda1.setFuncionario(funcionario1);
		vendaService.salvar(venda1);
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarVendas() {
		given().accept(ContentType.JSON).when().get().then().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarVendaExistente() {
		given().accept(ContentType.JSON).pathParam("id", venda1.getId()).when().get("/{id}").then()
				.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus404_QuandoConsultarVendaInexistente() {
		given().accept(ContentType.JSON).pathParam("id", idVendaInexistente).when().get("/{id}").then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarVendaCorreta() {
		given().body(jsonVendaCorreto).accept(ContentType.JSON).contentType(ContentType.JSON).when().post().then()
				.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarVendaSemValor() {
		given().body(jsonVendaIncorretoSemValor).accept(ContentType.JSON).contentType(ContentType.JSON).when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value()).body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarVendaValorIncorreto() {
		given().body(jsonVendaIncorretoValor).accept(ContentType.JSON).contentType(ContentType.JSON).when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value()).body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarVendaSemDescricao() {
		given().body(jsonVendaIncorretoSemDescricao).accept(ContentType.JSON).contentType(ContentType.JSON).when()
				.post().then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarVendaSemCaixa() {
		given().body(jsonVendaIncorretoSemCaixa).accept(ContentType.JSON).contentType(ContentType.JSON).when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value()).body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarVendaSemFuncionario() {
		given().body(jsonVendaIncorretoSemFuncionario).accept(ContentType.JSON).contentType(ContentType.JSON).when()
				.post().then().statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarVendaFuncionarioInexistente() {
		given().body(jsonVendaIncorretoFuncionarioInexistente).accept(ContentType.JSON).contentType(ContentType.JSON)
				.when().post().then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(VIOLACAO_NEGOCIO_PROBLEM_TYPE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarVendaCaixaInexistente() {
		given().body(jsonVendaIncorretoCaixaInexistente).accept(ContentType.JSON).contentType(ContentType.JSON).when()
				.post().then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(VIOLACAO_NEGOCIO_PROBLEM_TYPE));
	}

}
