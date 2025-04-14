package com.generation.blogpessoal.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

@RestController // primeiro passo
@RequestMapping("/postagens") // segundo passo
public class PostagemController {

	@Autowired
	private PostagemRepository postagemRepository; // ingetando a classe reoository dentro do controle, terceiro passo

	@GetMapping
	public ResponseEntity<List<Postagem>> getAll() { //
		return ResponseEntity.ok(postagemRepository.findAll()); // Estou dizendo que ele vai trazer a tabela pra mim,
																// atraves da lista
	}

}
