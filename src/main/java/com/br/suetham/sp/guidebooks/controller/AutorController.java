package com.br.suetham.sp.guidebooks.controller;

import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.br.suetham.sp.guidebooks.model.Autor;
import com.br.suetham.sp.guidebooks.repository.AutorRepository;
import com.br.suetham.sp.guidebooks.repository.TipoRepository;

@Controller
public class AutorController {
	
	@Autowired
	private AutorRepository repositoryAutor;
	@Autowired
	private TipoRepository repositoryTipo;

	@RequestMapping("cadastrarAutor")
	public String cadastraTipo(Model model) {
		model.addAttribute("autores", repositoryTipo.findAll());
		
		return "autor/cadastroAutor";
	}
	
	@RequestMapping("salvarAutor")
	public String salvaAutor(@Valid Autor autor, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			attr.addFlashAttribute("mensagemErro", "Verifique os campos...");
			return "redirect:cadastrarTipo";
		}
		try {
			
			repositoryAutor.save(autor);
			attr.addFlashAttribute("mensagemSucesso", "Autor cadastrado com sucesso. ID:" + autor.getId());
		} catch (Exception e) {
			attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar:" + e.getMessage());
		}
		return "redirect:cadastrarTipo";
		
	}

	@RequestMapping(value = "listaAutor/{page}", method = RequestMethod.GET)
	public String listarAutor(Long id, Model model, @PathVariable("page") int page) {
		PageRequest pegeable = PageRequest.of(page-1, 6, Sort.by(Sort.Direction.ASC, "id"));
		Page<Autor> pagina = repositoryAutor.findAll(pegeable);
	
		model.addAttribute("autores", pagina.getContent());
		int totalPages = pagina.getTotalPages();
		List<Integer> numPaginas = new ArrayList<Integer>();
		for(int i = 1; i <= totalPages; i++) {
		numPaginas.add(i);
	
	}
		model.addAttribute("numPaginas", numPaginas);
		model.addAttribute("totalPags", totalPages);
		model.addAttribute("pagAtual", page);
		
		return "autor/lista";
}

	@RequestMapping("excluirAutor")
	public String ExcluirAutor(Long id) {
		repositoryAutor.deleteById(id);
		return "redirect:listaAutor/1";
	}

	@RequestMapping("alterarAutor")
	public String alterar(Model model, Long id) {
		Autor autor = repositoryAutor.findById(id).get();
		model.addAttribute("autor", autor);
		return "forward:cadastrarAutor";
	}
}