package br.com.aluraflix.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.aluraflix.api.model.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {
	
	public Video findByTituloContaining(String titulo);

	@Query(nativeQuery = true, value = 
			  "SELECT "
			+ 	"* "
			+ "FROM video "
			+ "WHERE categoria_id = (:id)")
	public List<Video> findAllByCategoriaId(Long id);
	
}
