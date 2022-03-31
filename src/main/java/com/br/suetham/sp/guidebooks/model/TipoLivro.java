package com.br.suetham.sp.guidebooks.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
//gera os getter e setter do lombok
@Data
@Entity
public class TipoLivro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	@Column (unique = true)
	private String nome;
	private String descricao;
	@NotEmpty
	private String palavraChave;
	
}
   