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

import com.br.suetham.sp.guidebooks.model.Editora;
import com.br.suetham.sp.guidebooks.model.TipoLivro;
import com.br.suetham.sp.guidebooks.repository.EditorRepository;

@Controller
public class EditoraController {
	
	@Autowired
	private EditorRepository repository;

	@RequestMapping("cadastraEditora")
	public String cadastraEditora() {
		return "editora/cadastroEditora";
	}
	
	@RequestMapping(value = "salvarEditora", method = RequestMethod.POST)
	public String salvarEdiora(@Valid Editora editora, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			attr.addFlashAttribute("mensagemErro", "Verifique os campos...");
			return "redirect:cadastrarTipo";
			
		}
		try {
			// salvar no bd a entidade
			repository.save(editora);
			// adicionar uma mensagem
			attr.addFlashAttribute("mensagemSucesso", "Editora cadastrado com sucesso. ID:" + editora.getId());
		} catch (Exception e) {
			attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar:" + e.getMessage());
		}
		return "redirect:cadastrarTipo";
		
	}

	@RequestMapping("listaEditora/{page}")
	public String listaEditora(Model model, @PathVariable("page") int page) {
		PageRequest pegeable = PageRequest.of(page-1, 6, Sort.by(Sort.Direction.ASC, "id"));
		Page<Editora> pagina = repository.findAll(pegeable);
		model.addAttribute("editoras", pagina.getContent());
		int totalPages = pagina.getTotalPages();
		List<Integer> numPaginas = new ArrayList<Integer>();
		for(int i = 1; i <= totalPages; i++) {
		numPaginas.add(i);
		}
	
			model.addAttribute("numPaginas", numPaginas);
			model.addAttribute("totalPags", totalPages);
			model.addAttribute("pagAtual", page);
			
			return "editora/lista";
}
	}



