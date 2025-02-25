package br.com.empresa.api_comercio.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.empresa.api_comercio.dto.CategoryDTO;
import br.com.empresa.api_comercio.entities.Category;
import br.com.empresa.api_comercio.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.empresa.api_comercio.dto.ProductDTO;
import br.com.empresa.api_comercio.entities.Product;
import br.com.empresa.api_comercio.repositories.CategoryRepository;
import br.com.empresa.api_comercio.repositories.ProductRepository;
import br.com.empresa.api_comercio.services.exception.ResourceNotFoundException;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	@Override
	public Page<ProductDTO> findAllPaged(Pageable pageable) {

		Page<Product> page = repository.findAll(pageable);
		return page.map(x -> new ProductDTO(x, x.getCategories()));
	}

	@Transactional(readOnly = true)
	@Override
	public List<ProductDTO> queryMethod(String name) {

		List<Product> list = repository.findAllByNameContainingIgnoreCase(name);
		if(list.isEmpty()){
			throw new ResourceNotFoundException("Name not found: " + name);
		}
		return list.stream().map(x -> new ProductDTO(x, x.getCategories())).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	@Override
	public ProductDTO findById(UUID id) {

		Optional<Product> obj = repository.findById(id);

		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));

		return new ProductDTO(entity, entity.getCategories());
	}

	@Transactional
	@Override
	public ProductDTO insert(ProductDTO dto) {

		Product entity = new Product();
		copyDtoToEntity(entity, dto);
		repository.save(entity);
		return new ProductDTO(entity, entity.getCategories());
	}

	@Transactional
	@Override
	public ProductDTO update(UUID id, ProductDTO dto) {

		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
		copyDtoToEntity(entity, dto);
		repository.save(entity);
		return new ProductDTO(entity, entity.getCategories());
	}

	@Override
	public void deleteById(UUID id) {

		Optional<Product> obj = repository.findById(id);
		if (obj.isEmpty()) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}
		repository.deleteById(id);
	}


	public void copyDtoToEntity(Product entity, ProductDTO dto) {

		entity.setName(dto.getName());
		entity.setPrice(dto.getPrice());
		entity.setDescription(dto.getDescription());
		entity.setImgUrl(dto.getImgUrl());

		entity.getCategories().clear();

		if(dto.getCategories() != null && !dto.getCategories().isEmpty()){
			for(CategoryDTO catDto : dto.getCategories()){
				if(catDto.getId() != null){
					Optional<Category> obj = categoryRepository.findById(catDto.getId());
					Category category = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + catDto.getId()));
					entity.getCategories().add(category);
					category.getProducts().add(entity);
				}else{
					Category newCategory = new Category();
					newCategory.setName(catDto.getName());
					entity.getCategories().add(newCategory);
					newCategory.getProducts().add(entity);
				}
			}
		}

	}
}


