package com.br.suetham.sp.guidebooks.rest;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.br.suetham.sp.guidebooks.annotation.Privado;
import com.br.suetham.sp.guidebooks.annotation.Publico;
import com.br.suetham.sp.guidebooks.model.Erro;
import com.br.suetham.sp.guidebooks.model.Livro;
import com.br.suetham.sp.guidebooks.model.Usuario;
import com.br.suetham.sp.guidebooks.repository.UsuarioRepository;
import com.fasterxml.jackson.annotation.JacksonInject.Value;

@RestController
@RequestMapping("api/user")
public class UsuarioRestController {
	
	@Autowired
	private UsuarioRepository repository;
	
	@Publico
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Usuario> getLivro(@PathVariable("id") Long idUser){
		//tenta buscar o restaurente no repository
		Optional<Usuario> optional = repository.findById(idUser);
		//se o restaurente exister
		if(optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	//aqui so chega erro 500, qualquer outro tipo erro do front ;)
	@Publico
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criarUsuario(@RequestBody Usuario usuario){
		try{
		//insere o usuario no banco de dados
		repository.save(usuario);
		//retornar o codigo http 201, informa como acesser o recurso inserido
		//e acrescenta no corpo da resposta o objeto inserido
		return ResponseEntity.created(URI.create("/api/usuario/" + usuario.getId())).body(usuario);
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Registro Duplicado", e.getClass().getName());
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Contante o suporte", e.getClass().getName());
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> atualizarUsuario (@RequestBody Usuario usuario, @PathVariable("id") Long id){
		//validação do id
		if(id  != usuario.getId()) {
			throw new RuntimeException("ID Inválido");
		}
		repository.save(usuario);
		return ResponseEntity.ok().build();
	}
	@Privado
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluirUsuario(@PathVariable("id") Long idUsuario){
		repository.deleteById(idUsuario);
		return ResponseEntity.noContent().build();
	}

}
