package br.com.empresa.api_comercio.services;

import br.com.empresa.api_comercio.dto.*;
import br.com.empresa.api_comercio.entities.User;
import br.com.empresa.api_comercio.repositories.*;
import br.com.empresa.api_comercio.services.exception.ResourceNotFoundException;
import br.com.empresa.api_comercio.services.impl.UserServiceImpl;
import br.com.empresa.api_comercio.tests.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@SpringBootTest
@Transactional
public class UserServiceTests {

    @Autowired
    private UserServiceImpl service;

    @Autowired
    private UserRepository repository;

    @Test
    public void findAllPagedShouldReturnAllUsers(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<UserDTO> page = service.findAllPaged(pageable);

        Assertions.assertFalse(page.isEmpty());
        Assertions.assertNotNull(page);
    }

    @Test
    public void queryMethodShouldReturnListFilteredByFirstName() {

        String firstName = "Mari";

        List<UserDTO> list = service.queryMethod(firstName);

        Assertions.assertFalse(list.isEmpty());
        Assertions.assertNotNull(list);
    }

    @Test
    public void queryMethodShouldThrowResourceNotFoundExceptionWhenFirstNameNonExisting(){

        String firstName = "Tamara";

       Assertions.assertThrows(ResourceNotFoundException.class, () -> {
           List<UserDTO> list = service.queryMethod(firstName);
           throw new ResourceNotFoundException("First name not found: " + firstName);
       });
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExisting(){

        Optional<User> obj = repository.findAll().stream().findFirst();;
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        UserDTO dto = service.findById(id);

        Assertions.assertNotNull(dto);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            UserDTO dto = service.findById(id);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure(){

        UserDTO dto = Factory.createdUserDto();

        service.insert(dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(5, repository.count());
    }

    @Test
    public void updateShouldSaveObjectWhenIdExisting(){

        Optional<User> obj = repository.findAll().stream().findFirst();;
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        UserDTO dto = Factory.createdUserDtoToUpdate();

        dto = service.update(id, dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals( 4, repository.count());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            UserDTO dto = Factory.createdUserDtoToUpdateIsNotFound();
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }

    @Test
    public void deleteByIdShouldDeleteUserByIdWhenIdExisting(){

        Optional<User> obj = repository.findAll().stream().findFirst();;
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
