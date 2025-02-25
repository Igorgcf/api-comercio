package br.com.empresa.api_comercio.services;

import br.com.empresa.api_comercio.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    Page<ProductDTO> findAllPaged(Pageable pageable);

    List<ProductDTO> queryMethod(String name);

    ProductDTO findById(UUID id);

    ProductDTO insert(ProductDTO dto);

    ProductDTO update(UUID id, ProductDTO dto);

    void deleteById(UUID id);
}
