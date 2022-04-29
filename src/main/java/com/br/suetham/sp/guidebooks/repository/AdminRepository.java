package com.br.suetham.sp.guidebooks.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.br.suetham.sp.guidebooks.model.Administrador;


public interface AdminRepository extends PagingAndSortingRepository<Administrador, Long> {

	public List<Administrador> findByNomeLike(String nome);
	
	public Administrador findByEmailAndSenha(String email, String senha);
	

}
