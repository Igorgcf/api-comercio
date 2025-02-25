package br.com.empresa.api_comercio.entities;

import org.junit.jupiter.api.*;

import java.util.UUID;

public class RoleTests {

    @Test
    public void roleShouldHaveCorrectStructure(){

        Role entity = new Role();

        UUID id = UUID.randomUUID();

        entity.setId(id);
        entity.setAuthority("Boss");

        Assertions.assertNotNull(entity);
    }
}
