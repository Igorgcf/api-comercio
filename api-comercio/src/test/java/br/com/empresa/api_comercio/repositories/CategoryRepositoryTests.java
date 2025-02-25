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
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository repository;


    @Test
    public void findAllShouldReturnAllCategoriesPaged(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<Category> page = repository.findAll(pageable);

        Assertions.assertNotNull(page);
    }

    @Test
    public void queryMethodShouldReturnAllCategoryFilteredByName(){

        String name = "Doces";

        List<Category> list = repository.findAllByNameContainingIgnoreCase(name);

        Assertions.assertFalse(list.isEmpty());
        Assertions.assertNotNull(list);
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExisting(){

        Optional<Category> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        Optional<Category> optional = repository.findById(id);

        Assertions.assertNotNull(optional);
        Assertions.assertTrue(optional.isPresent());
    }

    @Test
    public void saveShouldSaveObjectWhenCorrectStructure(){

    Category entity = Factory.createdCategory();
    repository.save(entity);

    Assertions.assertEquals( 4, repository.count());
    }
}
