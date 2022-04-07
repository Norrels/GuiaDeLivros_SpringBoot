package com.br.suetham.sp.guidebooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.br.suetham.sp.guidebooks.model.PublicoAlvo;
import com.br.suetham.sp.guidebooks.repository.AutorRepository;
import com.br.suetham.sp.guidebooks.repository.EditorRepository;
import com.br.suetham.sp.guidebooks.repository.LivroRepository;
import com.br.suetham.sp.guidebooks.repository.TipoRepository;


@Controller
public class LivroController {

	@Autowired
	private TipoRepository repositoryTipo;
	@Autowired
	private AutorRepository repositoryAutor;
	@Autowired
	private EditorRepository repositoryEditora;
	
	@RequestMapping("cadastraLivro")
	public String cadastraLivro(Model model) {
		model.addAttribute("tipos", repositoryTipo.findAllByOrderByNomeAsc());
		model.addAttribute("autores", repositoryAutor.findAllByOrderByNomeAsc());
		model.addAttribute("editoras", repositoryEditora.findAllByOrderByNomeAsc());
		model.addAttribute("publicos", PublicoAlvo.values());
		
		return "livro/cadastro";
	}
}
