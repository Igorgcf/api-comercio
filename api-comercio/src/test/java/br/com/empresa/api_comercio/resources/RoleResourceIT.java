package br.com.empresa.api_comercio.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import br.com.empresa.api_comercio.dto.*;
import br.com.empresa.api_comercio.entities.Role;
import br.com.empresa.api_comercio.repositories.RoleRepository;
import br.com.empresa.api_comercio.services.exception.ResourceNotFoundException;
import br.com.empresa.api_comercio.services.impl.RoleServiceImpl;
import br.com.empresa.api_comercio.tests.*;
import com.fasterxml.jackson.databind.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoleServiceImpl service;

    @Autowired
    private RoleRepository repository;

    @Test
    public void findAllPagedShouldReturnAllRolePagedSortByAuthority() throws Exception {

        ResultActions result = mockMvc.perform(get("/roles?sort=authority"));
                result.andExpect(status().isOk());

                result.andExpect(jsonPath("$.content").exists());
                result.andExpect(jsonPath("$.content[0].id").exists());
                result.andExpect(jsonPath("$.content[0].authority").exists());
    }

    @Test
    public void queryMethodShouldReturnAllRoleFilteredByAuthority() throws Exception {

        String authority = "Manager";

        ResultActions result = mockMvc.perform(get("/roles/authority/{authority}", authority));
                result.andExpect(status().isOk());

                result.andExpect(jsonPath("$.[0].id").exists());
                result.andExpect(jsonPath("$.[0].authority").exists());
    }

    @Test
    public void queryMethodShouldReturnStatusNotFoundWhenAuthorityNonExists() throws Exception {

        String authority = "Developer";
        ResultActions result = mockMvc.perform(get("/roles/authority/{authority}", authority));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findByIdShouldReturnRoleByIdWhenIdExists() throws Exception {

        Optional<Role> obj = repository.findAll().stream().findFirst();;
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();
        ResultActions result = mockMvc.perform(get("/roles/{id}", id));
                result.andExpect(status().isOk());

                result.andExpect(jsonPath("$.id").exists());
                result.andExpect(jsonPath("$.authority").exists());
    }

    @Test
    public void findByIdShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

        UUID id = UUID.randomUUID();

        ResultActions result = mockMvc.perform(get("/roles/{id}", id));
                result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure() throws Exception {

            RoleDTO dto = Factory.createdRoleDto();

            String jsonBody = objectMapper.writeValueAsString(dto);

            ResultActions result = mockMvc.perform(post("/roles")
                    .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON));

                    result.andExpect(status().isOk());
                    result.andExpect(jsonPath("$.id").exists());
                    result.andExpect(jsonPath("$.authority").exists());
    }

    @Test
    public void updateShouldSaveRoleByIdWhenIdExists() throws Exception {

        Optional<Role> obj = repository.findAll().stream().findFirst();;
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        RoleDTO dto = Factory.createdRoleDtoToUpdate();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/roles/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

                result.andExpect(status().isOk());
                result.andExpect(jsonPath("$.id").exists());
                result.andExpect(jsonPath("$.authority").exists());
    }

    @Test
    public void updateShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception{

        UUID id = UUID.randomUUID();

        RoleDTO dto = Factory.createdRoleDtoToUpdateIsNotFound();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/roles/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

                result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteByIdShouldReturnBadRequestWhenIdIsAssociated() throws Exception {

        Optional<Role> obj = repository.findAll().stream().findFirst();;
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(delete("/roles/{id}", id));
                result.andExpect(status().isBadRequest());
    }

    @Test
    public void deleteByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting() throws Exception {

        UUID id = UUID.randomUUID();

        ResultActions result = mockMvc.perform(delete("/roles/{id}", id));
                result.andExpect(status().isNotFound());
    }
}
