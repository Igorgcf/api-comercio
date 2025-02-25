package br.com.empresa.api_comercio.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.empresa.api_comercio.dto.ProductDTO;
import br.com.empresa.api_comercio.entities.Product;
import br.com.empresa.api_comercio.repositories.ProductRepository;
import br.com.empresa.api_comercio.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.empresa.api_comercio.dto.CategoryDTO;
import br.com.empresa.api_comercio.entities.Category;
import br.com.empresa.api_comercio.repositories.CategoryRepository;
import br.com.empresa.api_comercio.services.exception.ResourceNotFoundException;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Autowired
	private ProductRepository productRepository;

	@Transactional(readOnly = true)
	@Override
	public Page<CategoryDTO> findAllPaged(Pageable pageable) {

		Page<Category> page = repository.findAll(pageable);

		return page.map(x -> new CategoryDTO(x, x.getProducts()));
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<CategoryDTO> queryMethod(String name) {
		
		List<Category> list = repository.findAllByNameContainingIgnoreCase(name);
		if(list.isEmpty()){
			throw new ResourceNotFoundException("Name not found: " + name);
		}
		return list.stream().map(x -> new CategoryDTO(x, x.getProducts())).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	@Override
	public CategoryDTO findById(UUID id) {

		Optional<Category> obj = repository.findById(id);

		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found" + id));

		return new CategoryDTO(entity, entity.getProducts());
	}

	@Transactional
	@Override
	public CategoryDTO insert(CategoryDTO dto) {

		Category entity = new Category();
		copyDtoToEntity(entity, dto);
		repository.save(entity);
		return new CategoryDTO(entity, entity.getProducts());
	}

	@Transactional
	@Override
	public CategoryDTO update(UUID id, CategoryDTO dto) {

		Optional<Category> obj = repository.findById(id);

		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
		copyDtoToEntity(entity, dto);
		repository.save(entity);
		return new CategoryDTO(entity, entity.getProducts());

	}

	@Override
	public void deleteById(UUID id) {

		Optional<Category> obj = repository.findById(id);
		if (obj.isEmpty()) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		repository.deleteById(id);
	}

	void copyDtoToEntity(Category entity, CategoryDTO dto){

		entity.setName(dto.getName());

		entity.getProducts().clear();

		if(dto.getProducts() != null && !dto.getProducts().isEmpty()){
			for(ProductDTO prodDto : dto.getProducts()){
				if(prodDto.getId() != null){
					Optional<Product> obj = productRepository.findById(prodDto.getId());
					Product product = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + prodDto.getId()));
					entity.getProducts().add(product);
					product.getCategories().add(entity);
				}else{
					Product newProduct = new Product();
					newProduct.setName(prodDto.getName());
					newProduct.setPrice(prodDto.getPrice());
					newProduct.setDescription(prodDto.getDescription());
					newProduct.setImgUrl(prodDto.getImgUrl());
					entity.getProducts().add(newProduct);
					newProduct.getCategories().add(entity);
				}
			}
		}
	}
}

