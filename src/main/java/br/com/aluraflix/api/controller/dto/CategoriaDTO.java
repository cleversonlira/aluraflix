package br.com.aluraflix.api.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.aluraflix.api.model.Categoria;

public class CategoriaDTO {

	private Long id;
	private String titulo;
	private String cor;
	
	public CategoriaDTO(Categoria categoria) {
		this.id = categoria.getId();
		this.titulo = categoria.getTitulo();
		this.cor = categoria.getCor();
	}

	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getCor() {
		return cor;
	}
	
	public static List<CategoriaDTO> converter(List<Categoria> categorias) {
		return categorias.stream().map(categoria -> new CategoriaDTO(categoria)).collect(Collectors.toList());
	}
	
}