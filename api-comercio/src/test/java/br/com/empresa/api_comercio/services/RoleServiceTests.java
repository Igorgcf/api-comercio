package br.com.empresa.api_comercio.services;

import br.com.empresa.api_comercio.dto.*;
import br.com.empresa.api_comercio.entities.Role;
import br.com.empresa.api_comercio.repositories.*;
import br.com.empresa.api_comercio.services.exception.*;
import br.com.empresa.api_comercio.services.impl.RoleServiceImpl;
import br.com.empresa.api_comercio.tests.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@SpringBootTest
@Transactional
public class RoleServiceTests {

    @Autowired
    private RoleServiceImpl service;

    @Autowired
    private RoleRepository repository;

    @Test
    public void findAllPagedShouldReturnAllRolePaged(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<RoleDTO> page = service.findAllPaged(pageable);

        Assertions.assertFalse(page.isEmpty());
        Assertions.assertNotNull(page);
    }

    @Test
    public void findByIdShouldReturnRoleByIdWhenIdExists(){

        Optional<Role> obj = repository.findAll().stream().findFirst();;
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        RoleDTO dto = service.findById(id);

        Assertions.assertNotNull(dto);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            RoleDTO dto = service.findById(id);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }

    @Test
    public void queryMethodShouldReturnListFilteredByAuthority(){

        String authority = "Manager";

        List<RoleDTO> list = service.queryMethod(authority);

        Assertions.assertFalse(list.isEmpty());
        Assertions.assertNotNull(list);
    }

    @Test
    public void queryMethodShouldThrowResourceNotFoundExceptionWhenAuthorityNonExisting(){

        String authority = "Boss";

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            List<RoleDTO> list = service.queryMethod(authority);
            throw new ResourceNotFoundException("Authority not found: " + authority);
        });
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure(){

        RoleDTO dto = Factory.createdRoleDto();

        service.insert(dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(4, repository.count());
    }

    @Test
    public void updateShouldSaveObjectWhenIdExisting(){

        Optional<Role> obj = repository.findAll().stream().findFirst();;
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        RoleDTO dto = Factory.createdRoleDtoToUpdate();

        service.update(id, dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(3, repository.count());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            RoleDTO dto = Factory.createdRoleDtoToUpdateIsNotFound();
            dto = service.update(id, dto);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }

    @Test
    public void deleteByIdShouldThrowDataIntegrityViolationExceptionWhenIdIsAssociated(){

        Optional<Role> obj = repository.findAll().stream().findFirst();;
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            service.deleteById(id);
            throw new DataIntegrityViolationException("Data Integrity Violation Exception.");
        });
    }

    @Test
    public void deleteByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.deleteById(id);
            throw new ResourceNotFoundException("Id not found: " +id);
        });
    }
}
