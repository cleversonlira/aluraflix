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

import br.com.aluraflix.api.controller.dto.VideoDTO;
import br.com.aluraflix.api.controller.form.VideoForm;
import br.com.aluraflix.api.model.Video;
import br.com.aluraflix.api.repository.CategoriaRepository;
import br.com.aluraflix.api.repository.VideoRepository;

@RestController
@RequestMapping("/videos")
public class VideoController {

	@Autowired
	private VideoRepository videoRepository;
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping //GetAll
	public List<VideoDTO> list() {
		return VideoDTO.converter(this.videoRepository.findAll());
	}
	
	@GetMapping("/{id}") //GetById
	public ResponseEntity<VideoDTO> detail(@PathVariable Long id) {
		Optional<Video> video = this.videoRepository.findById(id);
		if (video.isPresent()) {
			return ResponseEntity.ok(new VideoDTO(video.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/")
	public ResponseEntity<VideoDTO> buscaPorTitulo(@Valid String search) {
//		Optional<Video> videoOptional = this.videoRepository.findById(this.videoRepository.findByTituloContaining(search).getId());
//		videoOptional.orElseThrow(() -> new NullPointerException("Unavailable"));
//		return ResponseEntity.ok(new VideoDTO(videoOptional.get()));
		try {
			@Valid Video video = this.videoRepository.findByTituloContaining(search);
			return ResponseEntity.ok(new VideoDTO(video));
		} catch (NullPointerException e) {
			System.out.println(e);
			return ResponseEntity.notFound().build();
		}
//		return (!video2.isPresent()) 
//				? ResponseEntity.notFound().build()		
//				: ResponseEntity.ok(new VideoDTO(video2.get()));		
	}

	@PostMapping //Insert
	@Transactional 
	public ResponseEntity<VideoDTO> post( @RequestBody @Valid VideoForm videoForm, UriComponentsBuilder uriBuilder) {		
		Video video = videoForm.converter(categoriaRepository);
		this.videoRepository.save(video);
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(video.getId()).toUri();
		return ResponseEntity.created(uri).body(new VideoDTO(video));
	}

	@PutMapping("/{id}") //UpdateById
	@Transactional
	public ResponseEntity<VideoDTO> put(@PathVariable Long id, @RequestBody @Valid VideoForm videoForm) {
		Optional<Video> videoOptional = this.videoRepository.findById(id);
		if (videoOptional.isPresent()) {
			Video videoFound = videoOptional.get();
			videoFound.setTitulo(videoForm.getTitulo());
			videoFound.setDescricao(videoForm.getDescricao());
			videoFound.setUrl(videoForm.getUrl());
			return ResponseEntity.ok(new VideoDTO(videoFound));
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}") //DeleteById
	@Transactional
	public ResponseEntity<VideoDTO> remove(@PathVariable Long id) {		
		if (this.videoRepository.findById(id).isPresent()) {
			this.videoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

}