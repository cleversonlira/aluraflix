package br.com.aluraflix.api.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.aluraflix.api.model.Video;
import br.com.aluraflix.api.repository.VideoRepository;

@RestController
@RequestMapping("/videos")
public class VideoController {

	@Autowired
	private VideoRepository videoRepository;
	
	@GetMapping //GetAll
	public List<Video> list() {
		return this.videoRepository.findAll();
	}
	
	@GetMapping("/{id}") //GetById
	public ResponseEntity<Video> detail(@PathVariable Long id) {
		Optional<Video> video = this.videoRepository.findById(id);
		if (video.isPresent()) {
			return ResponseEntity.ok(video.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping //Insert
	@Transactional 
	public ResponseEntity<Video> post( @RequestBody @Valid Video video, UriComponentsBuilder uriBuilder) {		
		this.videoRepository.save(video);
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(video.getId()).toUri();
		return ResponseEntity.created(uri).body(video);
	}

	@PutMapping("/{id}") //UpdateById
	@Transactional
	public ResponseEntity<Video> put(@PathVariable Long id, @RequestBody @Valid Video video) {
		Optional<Video> videoOptional = this.videoRepository.findById(id);
		if (videoOptional.isPresent()) {
			Video videoFound = videoOptional.get();
			videoFound.setTitulo(video.getTitulo());
			videoFound.setDescricao(video.getDescricao());
			videoFound.setUrl(video.getUrl());
			return ResponseEntity.ok(videoFound);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}") //DeleteById
	@Transactional
	public ResponseEntity<Video> remove(@PathVariable Long id) {
		
		if (this.videoRepository.findById(id).isPresent()) {
			this.videoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

}