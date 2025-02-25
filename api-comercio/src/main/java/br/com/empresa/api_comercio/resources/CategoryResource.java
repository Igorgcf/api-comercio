package br.com.empresa.api_comercio.resources;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.empresa.api_comercio.dto.CategoryDTO;
import br.com.empresa.api_comercio.services.impl.CategoryServiceImpl;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

	@Autowired
	private CategoryServiceImpl service;

	@GetMapping
	public ResponseEntity<Page<CategoryDTO>> findAllPaged(Pageable pageable){
		
		Page<CategoryDTO> page = service.findAllPaged(pageable);
		if(!page.isEmpty()){
			for(CategoryDTO dto : page.toList()){
				dto.add(linkTo(methodOn(CategoryResource.class).findById(dto.getId())).withSelfRel());
			}
		}
		return ResponseEntity.ok().body(page);
	}
	
	@GetMapping(value = "/name/{name}")
	public ResponseEntity<List<CategoryDTO>> queryMethod(@PathVariable String name){
		
		List<CategoryDTO> list = service.queryMethod(name);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable UUID id){
		
		CategoryDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<CategoryDTO> insert(@Valid @RequestBody CategoryDTO dto){
		
		dto = service.insert(dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> update(@PathVariable UUID id, @Valid @RequestBody CategoryDTO dto){
		
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable UUID id){
		
		service.deleteById(id);
		return ResponseEntity.ok().body("Category deleted successfully.");
	}
}
