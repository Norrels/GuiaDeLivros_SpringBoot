package com.br.suetham.sp.guidebooks.rest;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.br.suetham.sp.guidebooks.annotation.Privado;
import com.br.suetham.sp.guidebooks.model.Avaliacao;
import com.br.suetham.sp.guidebooks.repository.AvaliacaoRepository;

@RestController
@RequestMapping("api/avaliacao")
public class AvaliacaoRestController {
	@Autowired
	private AvaliacaoRepository repository;
	
	@Privado
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Avaliacao> criarAvaliacao (@RequestBody Avaliacao avaliacao){
		repository.save(avaliacao);
		return ResponseEntity.created(URI.create("/api/avaliacao/"+ avaliacao.getId())).body(avaliacao);
		
	}

}
