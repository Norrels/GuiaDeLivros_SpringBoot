package com.br.suetham.sp.guidebooks.controller;

import java.awt.Dialog.ModalExclusionType;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Binding;
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
import com.br.suetham.sp.guidebooks.repository.AdminRepository;
import com.br.suetham.sp.guidebooks.util.HashUtil;
import com.google.common.hash.Hashing;


@Controller
public class AdministradorController {

	// Variavel para persistencias dos dados
	@Autowired
	private AdminRepository repository;

	@RequestMapping("cadastroAdm")
	public String formAdministrador() {
		return "administrador/cadastroadm";
	}

	// request mapping para salvar o administrador, do tipo POST
	@RequestMapping(value = "salvarAdmin", method = RequestMethod.POST)
	public String salvarAdmin(@Valid Administrador admin, BindingResult result, RedirectAttributes attr) {
		// Verificar se houveram erro na validação
		if (result.hasErrors()) {
			// Adicionar uma mensagens de erro
			attr.addFlashAttribute("mensagemErro", "Verifique os campos...");
			// redirecionar para o formulario
			return "redirect:cadastroAdm";
			
		}
		// variável para descobrir alteração ou inserção
		boolean alteracao = admin.getId() != null ? true : false;
		// Verifica se a senha está vazia
		if (admin.getSenha().equals(HashUtil.hash(""))) {
			if(!alteracao) {
			//Retirar a parte antes do @ do e-mail
			String parte = admin.getEmail().substring(0, admin.getEmail().indexOf("@"));
			//"setar" a parte na senha do admin
			admin.setSenha(parte);
			
			}else {
				// buscar a senha atual no banco
				String hash = repository.findById(admin.getId()).get().getSenha();
				//"Setar" o hash na senha
				admin.setSenhaComHash(hash);
			}
		}
		
		try {
			// salvar no bd a entidade
			repository.save(admin);
			// adicionar uma mensagem
			attr.addFlashAttribute("mensagemSucesso", "Administrador cadastrado com sucesso. ID:" + admin.getId());
		} catch (Exception e) {
			attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar:" + e.getMessage());
		}
		return "redirect:cadastroAdm";
	}
	
	@RequestMapping("listaAdm/{page}")
	public String listaAdmin(Model model, @PathVariable("page") int page) {
		//cria um pageable informando os paramentros da pagina
		PageRequest pegeable = PageRequest.of(page-1, 6, Sort.by(Sort.Direction.ASC, "id"));
		//cria um page de administrador atraves do paramentros passados pelo repository
		Page<Administrador> pagina = repository.findAll(pegeable);
		//Adicionar à Model a lista os admins
		model.addAttribute("admins", pagina.getContent());
		// variável para o total de páginas
		int totalPages = pagina.getTotalPages();
		//cria um List de inteiros para armazenar os nºs das páginas
		List<Integer> numPaginas = new ArrayList<Integer>();
		//prencher a list com as páginas
		for(int i = 1; i <= totalPages; i++) {
			//adicionar a página ao list
			numPaginas.add(i);
		}
		//adicionar os valores à model
		model.addAttribute("numPaginas", numPaginas);
		model.addAttribute("totalPags", totalPages);
		model.addAttribute("pagAtual", page);
		return "administrador/lista";
	}
	
	@RequestMapping("excluirAdm")
	public String excluirAdm(Long id) {
		repository.deleteById(id);
		return "redirect:listaAdm/1";
		
	}
	@RequestMapping("alterarAdm")
	public String alterarAdm(Long id, Model model) {
		Administrador admi = repository.findById(id).get();
		model.addAttribute("administrador", admi);
		return "forward:cadastroAdm";
		
	}

	@RequestMapping("buscarPorNome")
	public String buscarPorNome(String nome, Model model) {
		 model.addAttribute("clientes", repository.findByNomeLike(nome));
		return "lista";
	}
	
}

