package com.br.suetham.sp.guidebooks.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.br.suetham.sp.guidebooks.model.Administrador;
import com.br.suetham.sp.guidebooks.model.Editora;
import com.br.suetham.sp.guidebooks.model.Livro;
import com.br.suetham.sp.guidebooks.model.TipoLivro;

public interface LivroRepository extends PagingAndSortingRepository<Livro, Long> {
	
	public Iterable<Livro> findByTipoId(Long tipo);
}
