package com.br.suetham.sp.guidebooks.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.br.suetham.sp.guidebooks.model.Livro;
import com.br.suetham.sp.guidebooks.model.TipoLivro;
import com.br.suetham.sp.guidebooks.repository.LivroRepository;


@RestController
@RequestMapping("/api/livro")
public class LivroRestController {
	@Autowired
	private LivroRepository repository;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Livro> getLivros(){
		return repository.findAll();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Livro> getLivro(@PathVariable("id") Long idLivro){
		//tenta buscar o restaurente no repository
		Optional<Livro> optional = repository.findById(idLivro);
		//se o restaurente exister
		if(optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	@RequestMapping(value = "/tipo/{id}", method = RequestMethod.GET)
	public Iterable<Livro> getTipo(@PathVariable("id")Long id){
		return repository.findByTipoId(id);
	}
	
}
