package com.br.suetham.sp.guidebooks.controller;

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

import com.br.suetham.sp.guidebooks.model.Administrador;
import com.br.suetham.sp.guidebooks.model.TipoLivro;
import com.br.suetham.sp.guidebooks.repository.TipoRepository;

@Controller
public class TipoLivroController {
	
	@Autowired
	private TipoRepository repository;

	@RequestMapping("cadastrarTipo")
	public String cadastraTipo() {
		return "livro/cadastroTipo";
	}
	
	@RequestMapping("listaTipo/{page}")
	public String listaTipo(Model model, @PathVariable("page") int page){
		PageRequest pegeable = PageRequest.of(page-1, 6, Sort.by(Sort.Direction.ASC, "id"));
		Page<TipoLivro> pagina = repository.findAll(pegeable);
		model.addAttribute("admins", pagina.getContent());
		int totalPages = pagina.getTotalPages();
		List<Integer> numPaginas = new ArrayList<Integer>();
		for(int i = 1; i <= totalPages; i++) {
		numPaginas.add(i);
		}
		return "livro/listaTipo";
	}
	
	@RequestMapping(value = "salvarTipo", method = RequestMethod.POST)
	public String salvaTipo(@Valid TipoLivro tipo, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			// Adicionar uma mensagens de erro
			attr.addFlashAttribute("mensagemErro", "Verifique os campos...");
			// redirecionar para o formulario
			return "redirect:cadastrarTipo";
			
		}
		try {
			// salvar no bd a entidade
			repository.save(tipo);
			// adicionar uma mensagem
			attr.addFlashAttribute("mensagemSucesso", "Administrador cadastrado com sucesso. ID:" + tipo.getId());
		} catch (Exception e) {
			attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar:" + e.getMessage());
		}
		return "redirect:cadastrarTipo";
		
	}
}
