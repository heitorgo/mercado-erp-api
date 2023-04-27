package com.mercado;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.mercado.domain.model.Empresa;
import com.mercado.domain.model.Usuario;
import com.mercado.domain.service.EmpresaService;
import com.mercado.domain.service.UsuarioService;
import com.mercado.util.DatabaseCleaner;
import com.mercado.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class TesteEmpresaIT {

	@LocalServerPort
	private int port;
	private Empresa empresaAutoPecas;
	private String jsonEmpresaCorreta;
	private String jsonEmpresaIncorretaSemNome;
	private String jsonEmpresaIncorretaSemRazaoSocial;
	private String jsonEmpresaIncorretaSemUsuario;
	private String jsonEmpresaIncorretaUsuarioInexistente;
	private static final Long idEmpresaInexistente = 100L;
	private static final String DADOS_INVALIDOS_PROBLEM_TYPE = "Dados inválidos";
	private static final String VIOLACAO_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";
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
		RestAssured.basePath = "/empresas";

		databaseCleaner.clearTables();
		prepararDados();
		jsonEmpresaCorreta = ResourceUtils.getContentFromResource("/json/correto/empresa-correta.json");
		jsonEmpresaIncorretaSemNome = ResourceUtils
				.getContentFromResource("/json/incorreto/empresa/empresa-incorreta-sem-nome.json");
		jsonEmpresaIncorretaSemRazaoSocial = ResourceUtils
				.getContentFromResource("/json/incorreto/empresa/empresa-incorreta-sem-razaoSocial.json");
		jsonEmpresaIncorretaSemUsuario = ResourceUtils
				.getContentFromResource("/json/incorreto/empresa/empresa-incorreta-sem-usuario.json");
		jsonEmpresaIncorretaUsuarioInexistente = ResourceUtils
				.getContentFromResource("/json/incorreto/empresa/empresa-incorreta-usuario-inexistente.json");
	}

	private void prepararDados() {
		Usuario usuario1 = new Usuario();
		usuario1.setNome("José Teixeira");
		usuario1.setEmail("teixeiraze@hotmail.com");
		usuario1.setSenha("Teixeira&2020*03");
		usuarioService.salvar(usuario1);
		
		Usuario usuario2 = new Usuario();
		usuario2.setNome("Josivalda");
		usuario2.setEmail("jomaria@gmail.com");
		usuario2.setSenha("Jo#maria@2023");
		usuarioService.salvar(usuario2);
		
		empresaAutoPecas = new Empresa();
		empresaAutoPecas.setNome("Auto Peças Itu");
		empresaAutoPecas.setRazaoSocial("Auto Peças Itu LTDA");
		empresaAutoPecas.setUsuario(usuario1);
		empresaService.salvar(empresaAutoPecas);

		Empresa empresaAtelie = new Empresa();
		empresaAtelie.setNome("Atelie mãos de costura");
		empresaAtelie.setRazaoSocial("Atelie mãos de costura LTDA");
		empresaAtelie.setUsuario(usuario2);
		empresaService.salvar(empresaAtelie);

	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarEmpresas() {
		given().accept(ContentType.JSON).when().get().then().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarEmpresaExistente() {
		given().accept(ContentType.JSON).pathParam("id", empresaAutoPecas.getId()).when().get("/{id}").then()
				.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus404_QuandoConsultarEmpresaInexistente() {
		given().accept(ContentType.JSON).pathParam("id", idEmpresaInexistente).when().get("/{id}").then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarEmpresaCorreta() {
		given().body(jsonEmpresaCorreta).accept(ContentType.JSON).contentType(ContentType.JSON).when().post().then()
				.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarEmpresaSemNome() {
		given().body(jsonEmpresaIncorretaSemNome).accept(ContentType.JSON).contentType(ContentType.JSON).when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value()).body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarEmpresaSemRazaoSocial() {
		given().body(jsonEmpresaIncorretaSemRazaoSocial).accept(ContentType.JSON).contentType(ContentType.JSON).when()
				.post().then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarEmpresaSemUsuario() {
		given().body(jsonEmpresaIncorretaSemUsuario).accept(ContentType.JSON).contentType(ContentType.JSON).when()
				.post().then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarEmpresaUsuarioInexistente() {
		given().body(jsonEmpresaIncorretaUsuarioInexistente).accept(ContentType.JSON).contentType(ContentType.JSON).when()
				.post().then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(VIOLACAO_NEGOCIO_PROBLEM_TYPE));
	}

}
