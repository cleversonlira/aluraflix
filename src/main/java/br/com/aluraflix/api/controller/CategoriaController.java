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

import br.com.aluraflix.api.controller.dto.CategoriaDTO;
import br.com.aluraflix.api.controller.dto.VideoDTO;
import br.com.aluraflix.api.controller.form.CategoriaForm;
import br.com.aluraflix.api.model.Categoria;
import br.com.aluraflix.api.repository.CategoriaRepository;
import br.com.aluraflix.api.repository.VideoRepository;


@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	CategoriaRepository categoriaRepository; 
	
	@Autowired
	VideoRepository videoRepository;
	
	@GetMapping
	public List<CategoriaDTO> list() {
		return CategoriaDTO.converter(categoriaRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoriaDTO> getById(@PathVariable Long id) {
		Optional<Categoria> dto = categoriaRepository.findById(id);
		return dto.isPresent() 
				? ResponseEntity.ok(new CategoriaDTO(dto.get())) 
				: ResponseEntity.notFound().build();		
	}
	
	@PostMapping //Insert
	@Transactional 
	public ResponseEntity<CategoriaDTO> post(@RequestBody @Valid Categoria categoria, UriComponentsBuilder uriBuilder) {		
		this.categoriaRepository.save(categoria);
		URI uri = uriBuilder.path("/categorias/{id}").buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).body(new CategoriaDTO(categoria));
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<CategoriaDTO> edit(@PathVariable Long id, @RequestBody @Valid CategoriaForm categoriaForm) {
		return categoriaRepository.findById(id).isPresent()
				? ResponseEntity.ok(new CategoriaDTO(categoriaForm.update(id, categoriaRepository)))
				: ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<CategoriaDTO> remove(@PathVariable Long id) {		
		if (this.categoriaRepository.findById(id).isPresent()) {
			this.categoriaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{id}/videos/") //GetById
	public List<VideoDTO> getVideosByCategoria(@PathVariable Long id) {
		System.out.println("Passou pelo getVideosByCategoria");
		return VideoDTO.converter(this.videoRepository.findAllByCategoriaId(id));		
	}
	
}