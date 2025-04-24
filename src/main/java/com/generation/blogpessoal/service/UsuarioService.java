package com.generation.blogpessoal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.model.UsuarioLogin;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.security.JwtService;

import java.util.Optional;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	public Optional<Usuario> cadastrarUsuario(Usuario usuario) {

		if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
			return Optional.empty();

		usuario.setSenha(criptografarSenha(usuario.getSenha()));

		return Optional.of(usuarioRepository.save(usuario));
	}

	public Optional<Usuario> atualizarUsuario(Usuario usuario) {

		if (usuarioRepository.findById(usuario.getId()).isPresent()) {

			Optional<Usuario> buscarUsuario = usuarioRepository.findByUsuario(usuario.getUsuario()); // pesquisa se o
																										// usuario ja
																										// existe

			if ((buscarUsuario.isPresent()) && (buscarUsuario.get().getId()) != usuario.getId()) // se nao existir, ele
																									// aceita
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!");

			usuario.setSenha(criptografarSenha(usuario.getSenha())); // mantem a senha criptografada

			return Optional.ofNullable(usuarioRepository.save(usuario)); // retorna pra gente a senha criptografada

		}

		return Optional.empty();
	}

	public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {

		// Gera o Objeto de autenticacao,
		var credenciais = new UsernamePasswordAuthenticationToken(usuarioLogin.get().getUsuario(),
				usuarioLogin.get().getSenha());

		// autentica o Usuario, se deu certo vai acessar o banco de dados

		Authentication authentication = authenticationManager.authenticate(credenciais);

		// se a autenticacao foi efetuada com sucesso
		if (authentication.isAuthenticated()) {

			// busca de dados por usuario
			Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());

			// se o usuario for encontrado
			if (usuario.isPresent()) {

				// Preenche o Objeto usuarioLogin com dados encontrados
				usuarioLogin.get().setId(usuario.get().getId());
				usuarioLogin.get().setNome(usuario.get().getNome());
				usuarioLogin.get().setFoto(usuario.get().getFoto());
				usuarioLogin.get().setToken(gerarToken(usuarioLogin.get().getUsuario()));
				usuarioLogin.get().setSenha(""); // a senha vai ser gerada zerada
			}

		}

		return Optional.empty();
	}

	private String criptografarSenha(String senha) { // chama o metodo para criptografar a senha

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.encode(senha); // retornando minha senha
	}

	private String gerarToken(String ususario) { // estamos gerando um token
		return "Bearer " + jwtService.generateToken(ususario); // retorna o token pronto
	}
}
