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
public class UserRepositoryTests {

    @Autowired
    private UserRepository repository;

    @Test
    public void findAllPagedShouldReturnAllUsers(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<User> page = repository.findAll(pageable);

        Assertions.assertNotNull(page);
    }

    @Test
    public void queryMethodShouldReturnAllUserFilteredByName(){

        String firstName = "Maria";

        List<User> list = repository.findAllByFirstNameContainingIgnoreCase(firstName);

        Assertions.assertFalse(list.isEmpty());
        Assertions.assertNotNull(list);
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExisting(){

        Optional<User> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        Optional<User> optional = repository.findById(id);

        Assertions.assertNotNull(optional);
        Assertions.assertTrue(optional.isPresent());
    }

    @Test
    public void saveShouldSaveObjectWhenCorrectStructure(){

        User entity = Factory.createdUser();

        repository.save(entity);

        Assertions.assertEquals(5, repository.count());
    }

    @Test
    public void deleteByIdShouldDeleteObjectWhenIdExisting(){

        Optional<User> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        repository.deleteById(id);

        Assertions.assertEquals(3, repository.count());
    }
}
