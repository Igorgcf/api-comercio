package br.com.empresa.api_comercio.services;

import br.com.empresa.api_comercio.dto.*;
import br.com.empresa.api_comercio.entities.Product;
import br.com.empresa.api_comercio.repositories.*;
import br.com.empresa.api_comercio.services.exception.ResourceNotFoundException;
import br.com.empresa.api_comercio.services.impl.ProductServiceImpl;
import br.com.empresa.api_comercio.tests.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@SpringBootTest
@Transactional
public class ProductServiceTests {

    @Autowired
    private ProductServiceImpl service;

    @Autowired
    private ProductRepository repository;

    @Test
    public void findAllPagedShouldReturnAllProducts(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<ProductDTO> page = service.findAllPaged(pageable);

        Assertions.assertNotNull(page);
    }

    @Test
    public void queryMethodShouldReturnListFilteredByName(){

        String name = "Bolo";

        List<ProductDTO> list = service.queryMethod(name);

        Assertions.assertFalse(list.isEmpty());
        Assertions.assertNotNull(list);
    }

    @Test
    public void queryMethodShouldThrowResourceNotFoundExceptionWhenNameNonExisting(){

        String name = "Pão";

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            List<ProductDTO> list = service.queryMethod(name);
            throw new ResourceNotFoundException("Name not found: " + name);
        });
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExisting(){

        Optional<Product> obj = repository.findAll().stream().findFirst();;
        UUID existingId = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ProductDTO dto = service.findById(existingId);

        Assertions.assertNotNull(dto);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            ProductDTO dto = service.findById(id);
            throw new ResourceNotFoundException("Id not found:" + id);
        });
    }

    @Test
    public void insertShouldSaveObjectWhenObjectStructure(){

        ProductDTO dto = Factory.createdProductDto();

        service.insert(dto);

        Assertions.assertEquals( 5, repository.count());
    }

    @Test
    public void updateShouldSaveObjectWhenIdExisting() {

        Optional<Product> obj = repository.findAll().stream().findFirst();;
        UUID existingId = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ProductDTO dto = Factory.createdProductDto();

        service.update(existingId, dto);

        Assertions.assertEquals(4, repository.count());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            ProductDTO dto = Factory.createdProductDtoToUpdate();
            dto = service.update(id, dto);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }

    @Test
    public void deleteByIdShouldDeleteObjectWhenIdExisting(){

        Optional<Product> obj = repository.findAll().stream().findFirst();;
        UUID existingId = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        service.deleteById(existingId);

        Assertions.assertEquals(3, repository.count());
    }

    @Test
    public void deleteByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.deleteById(id);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }
}
