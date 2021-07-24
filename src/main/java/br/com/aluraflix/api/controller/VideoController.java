package br.com.aluraflix.api.controller;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.aluraflix.api.model.Video;
import br.com.aluraflix.api.repository.VideoRepository;

@RestController
@RequestMapping("/videos")
public class VideoController {

	@Autowired
	private VideoRepository videoRepository;
	
	@GetMapping
	public List<Video> list() {
		return this.videoRepository.findAll();
		//return Arrays.asList(new Video());
	}
	
	@GetMapping("/{id}")
	public Video detail(@PathVariable Long id) {
		return this.videoRepository.findById(id).get();
	}

	@PostMapping
	@Transactional
	public void post(@Valid @RequestBody Video video) {		
		this.videoRepository.save(video);
	}

	@PutMapping("/{id}")
	@Transactional
	public void put(@PathVariable Long id, @Valid @RequestBody Video video) {
		this.videoRepository.findById(id).get();		
	}

	@DeleteMapping("/{id}")
	@Transactional
	public void remove(@PathVariable Long id) {
		this.videoRepository.deleteById(id);
	}

}
