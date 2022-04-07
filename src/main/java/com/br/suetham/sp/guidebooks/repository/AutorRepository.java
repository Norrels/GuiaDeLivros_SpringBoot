package com.br.suetham.sp.guidebooks.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.br.suetham.sp.guidebooks.model.Autor;



public interface AutorRepository extends PagingAndSortingRepository<Autor, Long> {

	public List<Autor> findByGeneroId(Long id);

	public List<Autor> findAllByOrderByNomeAsc();


	
}
