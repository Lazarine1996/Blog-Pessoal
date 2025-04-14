package com.generation.blogpessoal.model;

import java.time.LocalDate;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity // criando esse Entity para criar a tabela no banco de dados, terceiro passo
@Table(name = "tb_postagens") // estou  dando um nome para a tabela, quarto passo
public class Postagem {
	
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)     // auto  inclemente, quinto passo
	private Long id;   // criando as variaveis, primeiro passo
	
	@NotBlank  // nao deixa cadastrar nulo nem vazio
	@Size(min = 3 , max = 100) // criando a quantidade minima e maximo de caracteres
	private String titulo;   // criando as variaveis, primeiro passo
	
	@NotBlank  // nao deixa cadastrar nulo nem vazio
	@Size(min = 10 , max = 1000) // criando a quantidade minima e maximo de caracteres
	private String texto;    // criando as variaveis, primeiro passo
	
	@UpdateTimestamp // pegar a data automaticamente
	private LocalDate data;   // criando as variaveis, primeiro passo
	
	// segundo passo, criar geters e seters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	
	

}
