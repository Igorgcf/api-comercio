package br.com.empresa.api_comercio.repositories;

import br.com.empresa.api_comercio.entities.*;
import br.com.empresa.api_comercio.services.exception.ResourceNotFoundException;
import br.com.empresa.api_comercio.tests.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.data.domain.*;

import java.util.*;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    @Test
    public void findAllPagedShouldReturnAllProducts(){

        PageRequest pageable = PageRequest.of(0,12);

        Page<Product> page = repository.findAll(pageable);

        Assertions.assertNotNull(page);
    }

    @Test
    public void queryMethodShouldReturnAllProductFilteredByName(){

        String name = "Bolo";

        List<Product> list = repository.findAllByNameContainingIgnoreCase(name);

        Assertions.assertFalse(list.isEmpty());
        Assertions.assertNotNull(list);
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExisting(){

        Optional<Product> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        Optional<Product> optional = repository.findById(id);

        Assertions.assertNotNull(optional);
        Assertions.assertTrue(optional.isPresent());
    }

    @Test
    public void saveShouldSaveObjectWhenCorrectStructure(){

        Product entity = Factory.createdProduct();

        repository.save(entity);

        Assertions.assertEquals(5, repository.count());
    }

    @Test
    public void deleteByIdShouldDeleteObjectWhenIdExisting(){

        Optional<Product> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        repository.deleteById(id);

        Assertions.assertEquals( 3, repository.count());
    }
}
