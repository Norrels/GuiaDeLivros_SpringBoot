package com.br.suetham.sp.guidebooks.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.br.suetham.sp.guidebooks.model.Editora;

public interface EditorRepository extends PagingAndSortingRepository<Editora, Long>{

	public List<Editora> findAllByOrderByNomeAsc();

}
