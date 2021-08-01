package br.com.aluraflix.api.controller.form;

import javax.validation.Valid;

import br.com.aluraflix.api.model.Categoria;
import br.com.aluraflix.api.repository.CategoriaRepository;

public class CategoriaForm {
	
	private String titulo;
	private String cor;
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCor() {
		return cor;
	}
	
	public void setCor(String cor) {
		this.cor = cor;
	}
	
	public Categoria update(@Valid Long id, CategoriaRepository categoriaRepository) {
		Categoria categoria = categoriaRepository.getById(id);
		categoria.setTitulo(this.titulo);
		categoria.setCor(this.cor);
		return categoria;
	}
}