package br.com.aluraflix.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.aluraflix.api.model.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {
	
	public Video findByTituloContaining(String titulo);
	
}
