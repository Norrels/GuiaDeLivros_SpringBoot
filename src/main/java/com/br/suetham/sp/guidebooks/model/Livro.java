package com.br.suetham.sp.guidebooks.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Livro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@Column(columnDefinition = "TEXT")
	private String sinopse;
	@ManyToOne
	private Autor autor;
	@ManyToOne
	private Editora editora;
	private int numPags;
	private String publicoAlvo;
	@ManyToOne
	private TipoLivro tipo;
	private String ISBN;
	private String anaEdicao;
	@Column(columnDefinition = "TEXT")
	private String fotos;
	private String idioma;
	
}
