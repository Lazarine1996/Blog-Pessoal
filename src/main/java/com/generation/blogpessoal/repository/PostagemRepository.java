package com.generation.blogpessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.blogpessoal.model.Postagem;

// primeiro passo, instanciar e importar o JpaRepository com os metodos
public interface PostagemRepository extends JpaRepository<Postagem, Long>{

	List<Postagem> findAll(); 
	

}
