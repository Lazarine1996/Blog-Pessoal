package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

import jakarta.validation.Valid;

@RestController // primeiro passo
@RequestMapping("/postagens") // segundo passo
@CrossOrigin(origins = "*", allowedHeaders = "*") // quarto passo, para buscar
public class PostagemController {

	@Autowired
	private PostagemRepository postagemRepository; // ingetando a classe reoository dentro do controle, terceiro passo

	@GetMapping // 5 passo
	public ResponseEntity<List<Postagem>> getAll() { //
		return ResponseEntity.ok(postagemRepository.findAll()); // Estou dizendo que ele vai trazer a tabela pra mim,
																// atraves da lista
	}

	// 6 passo
	@GetMapping("/{id}") // a barra é o caminho http, id entre chaves pq ele vai ser passado como valor
	public ResponseEntity<Postagem> getById(@PathVariable Long id) { //
		return postagemRepository.findById(id) // assiona muinha repository e vai me retornar se tem algo
				.map(resposta -> ResponseEntity.ok(resposta)) // aqui ele comunica com o meu banco de dados, caso de
																// certo
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // caso não for encontrado nada, apenas
	} // vai mostrar

	@GetMapping("/titulo/{titulo}") // para buscar por titulo
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo) { //
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo)); // busca especifica
		// ignore case ignora maiusculo ou minusculo por titulo
	}

	@PostMapping
	public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem) {
		return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
		// ele retorna o metodo do corpo do programa. Verbo post. Se tiver mais de um,
		// tem que identificar quem é quem
		// metodo cadastrar
	}

	@PostMapping
	public ResponseEntity<Postagem> put(@Valid @RequestBody Postagem postagem) {
		return postagemRepository.findById(postagem.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Postagem> postagem = postagemRepository.findById(id);

		if (postagem.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		postagemRepository.deleteById(id);

	}

}
