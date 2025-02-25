package br.com.empresa.api_comercio.services;

import br.com.empresa.api_comercio.dto.*;
import br.com.empresa.api_comercio.entities.Category;
import br.com.empresa.api_comercio.repositories.*;
import br.com.empresa.api_comercio.services.exception.ResourceNotFoundException;
import br.com.empresa.api_comercio.services.impl.CategoryServiceImpl;
import br.com.empresa.api_comercio.tests.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@SpringBootTest
public class CategoryServiceTests {

    @Autowired
    private CategoryServiceImpl service;

    @Autowired
    private CategoryRepository repository;


    @Test
    public void findAllPagedShouldReturnAllCategories(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<CategoryDTO> page = service.findAllPaged(pageable);

        Assertions.assertNotNull(page);
    }

    @Test
    public void queryMethodShouldReturnAllCategoryFilteredByName(){

        String name = "Doces";

        List<CategoryDTO> list = service.queryMethod(name);

        Assertions.assertFalse(list.isEmpty());
        Assertions.assertNotNull(list);
    }

    @Test
    public void queryMethodShouldThrowResourceNotFoundExceptionWhenNameNonExisting(){

        String name = "Salgados";

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
           List<CategoryDTO> list = service.queryMethod(name);
           throw new ResourceNotFoundException("Name not found: " + name);
        });
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExisting(){

        Optional<Category> obj = repository.findAll().stream().findFirst();;
        UUID existingId = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        CategoryDTO dto = service.findById(existingId);

        Assertions.assertNotNull(dto);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class,  () -> {
            Optional<Category> obj = repository.findById(id);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure(){

        CategoryDTO dto = Factory.createdCategoryDto();

        dto = service.insert(dto);

        Assertions.assertEquals(4, repository.count());
    }

    @Test
    public void updateShouldSaveObjectWhenIdExisting(){

        Optional<Category> obj = repository.findAll().stream().findFirst();;
        UUID existingId = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        CategoryDTO dto = Factory.createdCategoryDtoToUpdate();

        dto = service.update(existingId, dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(3, repository.count());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            CategoryDTO dto = Factory.createdCategoryDtoToUpdate();
            dto = service.update(id, dto);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }

    @Test
    public void deleteByIdShouldThrowDataIntegrityViolationExceptionWhenIdIsAssociated(){

        Optional<Category> obj = repository.findAll().stream().findFirst();;
        UUID existingId = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            service.deleteById(existingId);
            throw new DataIntegrityViolationException("Data Integrity Violation Exception.");
        });
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
