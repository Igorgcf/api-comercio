package br.com.empresa.api_comercio.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.empresa.api_comercio.entities.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID>{

	List<Category> findAllByNameContainingIgnoreCase(@Param("name") String name);

	boolean existsByName(String name);
}
