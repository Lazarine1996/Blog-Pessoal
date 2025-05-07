package com.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class usuarioControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@BeforeAll
	void start() {

		usuarioRepository.deleteAll();

		usuarioService.cadastrarUsuario(new Usuario(0L, "root", "root@root.com", "rootroot", "*"));
	}

	@Test
	@DisplayName("Cadastrar Usuario")
	public void deveCriarUmUsuario() {

		HttpEntity<Usuario> corpoRequisicaoEntity = new HttpEntity<Usuario>(
				new Usuario(0L, "Luan", "luanlazarine@gmail.com", "34824477", "*"));

		ResponseEntity<Usuario> corpoRespostaEntity = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST,
				corpoRequisicaoEntity, Usuario.class);

		assertEquals(HttpStatus.CREATED, corpoRespostaEntity.getStatusCode());
	}

	@Test
	@DisplayName("Não Permitir a Duplicação do Usuário")
	public void naoDeveDuplicarUsuario1() {

		usuarioService
				.cadastrarUsuario(new Usuario(0L, "Matheus da Silva", "matheusdasilva@gmail.com", "12345678", "-"));

		HttpEntity<Usuario> corpoRequisicaoEntity = new HttpEntity<Usuario>(
				new Usuario(0L, "Matheus da Silva", "matheusdasilva@gmail.com", "12345678", "-"));

		ResponseEntity<Usuario> corpoRespostaEntity = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST,
				corpoRequisicaoEntity, Usuario.class);

		assertEquals(HttpStatus.CREATED, corpoRespostaEntity.getStatusCode());
	}

	@Test
	@DisplayName("Atualizar um Usuário")
	public void deveAtualizarUmUsuario() {

		Optional<Usuario> usuarioCadastrado = usuarioService
				.cadastrarUsuario(new Usuario(0L, "Suely Santana", "suelysantana@gmail.com", "Suely123", "-"));

		Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(), "Suely Lazarine",
				"suelylazarine@gmail.com", "Suely123", "-");

		HttpEntity<Usuario> corpoRequisicaoEntity = new HttpEntity<Usuario>(usuarioUpdate);

		ResponseEntity<Usuario> corpoRespostaEntity = testRestTemplate.withBasicAuth("root@root.com", "rootroot")
				.exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicaoEntity, Usuario.class);

		assertEquals(HttpStatus.OK, corpoRespostaEntity.getStatusCode());
	}

	@Test
	@DisplayName("Não Permitir a Duplicação do Usuário")
	public void naoDeveDuplicarUsuario() {

		usuarioService
				.cadastrarUsuario(new Usuario(0L, "Matheus da Silva", "matheusdasilva@gmail.com", "87654321", "-"));

		HttpEntity<Usuario> corpoRequisicaoEntity = new HttpEntity<Usuario>(
				new Usuario(0L, "Matheus da Silva", "matheusdasilva@gmail.com", "87654321", "-"));

		ResponseEntity<Usuario> corpoRespostaEntity = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST,
				corpoRequisicaoEntity, Usuario.class);

		assertEquals(HttpStatus.CREATED, corpoRespostaEntity.getStatusCode());
	}

	@Test
	@DisplayName("Listar Todos os Usuários")
	public void deveMostrarTodosUsuarios() {

		usuarioService.cadastrarUsuario(new Usuario(0L, "Jose Carlos", "josecarlos@gmail.com", "jose1234", "-"));

		usuarioService.cadastrarUsuario(new Usuario(0L, "Jose Carlos", "josecarlos@gmail.com", "jose1234", "-"));

		ResponseEntity<String> reposta = testRestTemplate.withBasicAuth("root@root.com", "rootroot")
				.exchange("/usuarios/aLL", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, reposta.getStatusCode());
	}
}
