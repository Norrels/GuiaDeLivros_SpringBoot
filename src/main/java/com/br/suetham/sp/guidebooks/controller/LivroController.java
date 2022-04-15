package com.br.suetham.sp.guidebooks.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.br.suetham.sp.guidebooks.model.Autor;
import com.br.suetham.sp.guidebooks.model.Editora;
import com.br.suetham.sp.guidebooks.model.Livro;
import com.br.suetham.sp.guidebooks.model.PublicoAlvo;
import com.br.suetham.sp.guidebooks.repository.AutorRepository;
import com.br.suetham.sp.guidebooks.repository.EditorRepository;
import com.br.suetham.sp.guidebooks.repository.LivroRepository;
import com.br.suetham.sp.guidebooks.repository.TipoRepository;
import com.br.suetham.sp.guidebooks.util.FirebaseUtil;


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
	@Autowired
	private FirebaseUtil fireutil;
	
	
	
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
		//String para armazenar as URLS
		String fotos = livro.getFotos();
		for(MultipartFile arquivo : fileFotos) {
			//verifica se o arquivo existe
			if(arquivo.getOriginalFilename().isEmpty()) {
				//poderia ser uma break mas o continue pode evitar um erro caso alguma img estiver conronpida
				continue;
			}
			try {
				//faz upload e guarda a URL na strings fotos
				fotos += fireutil.upload(arquivo)+";";
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			
		}
		//guarda na variavel restaurante as fotos
		livro.setFotos(fotos);
		//salvar no BD
		repositoryLivro.save(livro);
		return "redirect:cadastraLivro";
	}
	
	@RequestMapping("listaLivro/{page}")
	public String listaLivro (Model model, @PathVariable("page") int page) {
		PageRequest pegeable = PageRequest.of(page-1, 6, Sort.by(Sort.Direction.ASC, "id"));
		Page<Livro> pagina = repositoryLivro.findAll(pegeable);
		model.addAttribute("livros", pagina.getContent());
		int totalPages = pagina.getTotalPages();
		List<Integer> numPaginas = new ArrayList<Integer>();
		for(int i = 1; i <= totalPages; i++) {
		numPaginas.add(i);
		}
		model.addAttribute("numPaginas", numPaginas);
		model.addAttribute("totalPags", totalPages);
		model.addAttribute("pagAtual", page);
		return "livro/lista";
	}
	

	@RequestMapping("excluirLivro")
	public String ExcluirAutor(Long id) {
		Livro livro = repositoryLivro.findById(id).get();
		if(livro.getFotos().length() > 0) {
			for(String foto : livro.verFotos()) {
				fireutil.deletar(foto);
			}
		}
		repositoryLivro.delete(livro);
		return "redirect:listaLivro/1";
	}

	@RequestMapping("alterarLivro")
	public String alterar(Model model, Long id) {
		Livro livro = repositoryLivro.findById(id).get();
		model.addAttribute("livro", livro);
		return "forward:/cadastraLivro";
	}
	@RequestMapping("excluirFotos")
	public String excluirFotos(Long idLivro, int numFoto, Model model) {
		//buscar o livro
		Livro livro = repositoryLivro.findById(idLivro).get();
		//buscar a URL da foto
		String urlFoto = livro.verFotos()[numFoto];
		//deleta a foto
		fireutil.deletar(urlFoto);
		//remover a url do atributo fotos
		livro.setFotos(livro.getFotos().replace(urlFoto+";", ""));
		//salvar no BD
		repositoryLivro.save(livro);
		//colocar o livro na model
		model.addAttribute("livro", livro);
		return "forward:/cadastraLivro";
	}
}

