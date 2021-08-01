package br.com.aluraflix.api.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.aluraflix.api.model.Video;

public class VideoDTO {

	Long id;
	String titulo;
	String descricao;
	String url;
	Long categoriaId;
	
	public VideoDTO(Video video) {
		this.id = video.getId();
		this.titulo = video.getTitulo();
		this.descricao = video.getDescricao();
		this.url = video.getUrl();
		this.categoriaId = video.getCategoria().getId();
		System.out.println("Construiu VideoDTO");
		System.out.println(video.getCategoria().getId());
	}

	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getUrl() {
		return url;
	}

	public Long getCategoriaId() {
		return categoriaId;
	}

	public static List<VideoDTO> converter(List<Video> videos) {
		return videos.stream().map(video -> new VideoDTO(video)).collect(Collectors.toList());
	}
	
}