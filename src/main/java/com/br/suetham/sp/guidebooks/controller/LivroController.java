package com.br.suetham.sp.guidebooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.br.suetham.sp.guidebooks.model.Livro;
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
	@Autowired
	private LivroRepository repositoryLivro;
	
	
	
	@RequestMapping("cadastraLivro")
	public String cadastraLivro(Model model) {
		model.addAttribute("tipos", repositoryTipo.findAllByOrderByNomeAsc());
		model.addAttribute("autores", repositoryAutor.findAllByOrderByNomeAsc());
		model.addAttribute("editoras", repositoryEditora.findAllByOrderByNomeAsc());
		model.addAttribute("publicos", PublicoAlvo.values());
		
		return "livro/cadastro";
	}
	@RequestMapping("salvarRestaurante")
	public String salvar(Livro livro , @RequestParam("fileFotos") MultipartFile[] fileFotos) {
		///repositoryLivro.save(livro);
		System.out.println(fileFotos.length);
		return "redirect:cadastraLivro";
	}
}
