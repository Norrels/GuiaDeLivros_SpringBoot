package com.br.suetham.sp.guidebooks.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.br.suetham.sp.guidebooks.util.HashUtil;

import lombok.Data;

//cria os getters e setters
@Data
//mapeia a entidade para o jpa
@Entity
public class Administrador {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String nome;
	//define a coluna email com um indice unico
	@Column(unique = true)
	@Email
	private String email;
	@NotEmpty
	private String senha;
	
	//Método set que aplicar o hash na senha
	public void setSenha(String senha){
		this.senha = HashUtil.hash(senha);
	}
	//Método que "seta" o hash na senha
	public void setSenhaComHash(String hash) {
		this.senha = hash;
	}
}