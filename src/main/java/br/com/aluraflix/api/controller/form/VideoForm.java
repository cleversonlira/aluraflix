package br.com.aluraflix.api.controller.form;

import br.com.aluraflix.api.model.Video;
import br.com.aluraflix.api.repository.CategoriaRepository;

public class VideoForm {

	private String titulo;
    private String descricao;
    private String url;
    private Long categoriaId;
    
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getCategoriaId() {
		return categoriaId;
	}
	public void setCategoriaId(Long categoriaTitulo) {
		this.categoriaId = categoriaTitulo;
	}
	
	public Video converter(VideoForm videoForm, CategoriaRepository repository) {
		Video video = new Video();
		video.setTitulo(this.titulo);
		video.setDescricao(this.descricao);
		video.setUrl(this.url);		
		video.setCategoria(repository.findById(this.categoriaId).get());
		return video;
	}
}