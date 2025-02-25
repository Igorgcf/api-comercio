package br.com.empresa.api_comercio.services;

import br.com.empresa.api_comercio.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    Page<CategoryDTO> findAllPaged(Pageable pageable);

    List<CategoryDTO> queryMethod(String name);

    CategoryDTO findById(UUID id);

    CategoryDTO insert(CategoryDTO dto);

    CategoryDTO update(UUID id, CategoryDTO dto);

    void deleteById(UUID id);
}
