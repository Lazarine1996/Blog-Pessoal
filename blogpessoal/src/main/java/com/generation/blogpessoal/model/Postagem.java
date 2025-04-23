package com.generation.blogpessoal.model;

import java.time.LocalDate;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity // criando esse Entity para criar a tabela no banco de dados, terceiro passo
@Table(name = "tb_postagens") // estou dando um nome para a tabela, quarto passo
public class Postagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto inclemente, quinto passo
	private Long id; // criando as variaveis, primeiro passo

	@NotBlank // nao deixa cadastrar nulo nem vazio
	@Size(min = 3, max = 100) // criando a quantidade minima e maximo de caracteres
	private String titulo; // criando as variaveis, primeiro passo

	@NotBlank // nao deixa cadastrar nulo nem vazio
	@Size(min = 10, max = 1000) // criando a quantidade minima e maximo de caracteres
	private String texto; // criando as variaveis, primeiro passo

	@UpdateTimestamp // pegar a data automaticamente
	private LocalDate data; // criando as variaveis, primeiro passo

	@ManyToOne // parte 2
	@JsonIgnoreProperties("postagem") // estou configurando o looping infinito quando eu imprimir a postagem e tema
	private Tema tema; // logo apos criar os geters e seters

	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Usuario usuario;

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

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
