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
public class RoleRepositoryTests {

    @Autowired
    private RoleRepository repository;

    @Test
    public void findAllPagedShouldReturnAllRoles(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<Role> page = repository.findAll(pageable);

        Assertions.assertNotNull(page);
    }

    @Test
    public void queryMethodShouldReturnAllRoleFilteredByAuthority(){

        String authority = "Manager";

        List<Role> list = repository.findAllByAuthorityContainingIgnoreCase(authority);

        Assertions.assertFalse(list.isEmpty());
        Assertions.assertNotNull(list);
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExisting(){

        Optional<Role> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        Optional<Role> optional = repository.findById(id);

        Assertions.assertNotNull(optional);
        Assertions.assertTrue(optional.isPresent());
    }

    @Test
    public void saveShouldSaveObjectWhenCorrectStructure(){

        Role entity = Factory.createdRole();

        repository.save(entity);

        Assertions.assertEquals(4, repository.count());
    }
}
