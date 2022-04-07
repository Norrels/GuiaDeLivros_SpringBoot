package com.br.suetham.sp.guidebooks.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.br.suetham.sp.guidebooks.model.TipoLivro;

public interface TipoRepository extends PagingAndSortingRepository<TipoLivro, Long> {
	public List<TipoLivro> findAllByOrderByNomeAsc();
}
