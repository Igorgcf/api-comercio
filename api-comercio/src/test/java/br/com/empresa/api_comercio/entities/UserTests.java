package br.com.empresa.api_comercio.entities;

import org.junit.jupiter.api.*;

import java.util.UUID;

public class UserTests {

    @Test
    public void userShouldHaveCorrectStructure(){

        User entity = new User();
        Role role = new Role();

        UUID id = UUID.randomUUID();

        role.setId(id);
        role.setAuthority("Manager");

        entity.setId(id);
        entity.setFirstName("Ana");
        entity.setLastName("Oliveira");
        entity.setEmail("ana@gmail.com");
        entity.setPassword("123456");

        entity.getRoles().add(role);

        Assertions.assertNotNull(entity);
        Assertions.assertNotNull(entity.getRoles());
    }
}
