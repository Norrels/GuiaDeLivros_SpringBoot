package com.br.suetham.sp.guidebooks.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.br.suetham.sp.guidebooks.model.Livro;


public interface LivroRepository extends PagingAndSortingRepository<Livro, Long> {
	
	public Iterable<Livro> findByTipoId(Long tipo);
}
