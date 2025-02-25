package br.com.empresa.api_comercio.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import br.com.empresa.api_comercio.dto.*;
import br.com.empresa.api_comercio.entities.User;
import br.com.empresa.api_comercio.repositories.UserRepository;
import br.com.empresa.api_comercio.services.exception.*;
import br.com.empresa.api_comercio.services.impl.UserServiceImpl;
import br.com.empresa.api_comercio.tests.*;
import com.fasterxml.jackson.databind.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;

import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserServiceImpl service;

    @Autowired
    private UserRepository repository;

    @Test
    public void findAllPagedShouldReturnAllUsersSortByName() throws Exception {

        ResultActions result = mockMvc.perform(get("/users?sort=firstName"));

                result.andExpect(status().isOk());
                result.andExpect(jsonPath("$.content").exists());
                result.andExpect(jsonPath("$.content[0].id").exists());
                result.andExpect(jsonPath("$.content[0].firstName").exists());
                result.andExpect(jsonPath("$.content[0].lastName").exists());
                result.andExpect(jsonPath("$.content[0].email").exists());
    }

    @Test
    public void queryMethodShouldReturnListFilteredByFirstName() throws Exception {

        String firstName = "Gustavo";

        ResultActions result = mockMvc.perform(get("/users/firstName/{firstName}", firstName));

                result.andExpect(status().isOk());
                result.andExpect(jsonPath("$[0].id").exists());
                result.andExpect(jsonPath("$[0].firstName").value(firstName));
                result.andExpect(jsonPath("$[0].lastName").exists());
                result.andExpect(jsonPath("$[0].email").exists());
    }

    @Test
    public void queryMethodShouldReturnStatusNotFoundWhenFirstNameNonExisting() throws Exception {

        String firstName = "João";
        ResultActions result = mockMvc.perform(get("/users/firstName/{firstName}", firstName));

        result.andExpect(status().isNotFound());
    }


    @Test
    public void findByIdShouldReturnUserByIdWhenIdExists() throws Exception {

        Optional<User> obj = repository.findAll().stream().findFirst();;
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(get("/users/{id}", id));
                result.andExpect(status().isOk());

                result.andExpect(jsonPath("$.id").exists());
                result.andExpect(jsonPath("$.firstName").exists());
                result.andExpect(jsonPath("$.lastName").exists());
                result.andExpect(jsonPath("$.email").exists());
    }

    @Test
    public void findByIdShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

        UUID id = UUID.randomUUID();

        ResultActions result = mockMvc.perform(get("/users/{id}", id)
                .accept(MediaType.APPLICATION_JSON));
                result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure() throws Exception {

        UserDTO dto = Factory.createdUserDto();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/users")
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

                result.andExpect(status().isOk());
                result.andExpect(jsonPath("$.id").exists());
                result.andExpect(jsonPath("$.firstName").exists());
                result.andExpect(jsonPath("$.lastName").exists());
                result.andExpect(jsonPath("$.email").exists());
    }

    @Test
    public void updateShouldSaveObjectWhenIdExists() throws Exception {

        Optional<User> obj = repository.findAll().stream().findFirst();;
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        UserDTO dto = Factory.createdUserDtoToUpdate();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/users/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

                 result.andExpect(status().isOk());
                result.andExpect(jsonPath("$.id").exists());
                result.andExpect(jsonPath("$.firstName").exists());
                result.andExpect(jsonPath("$.lastName").exists());
                result.andExpect(jsonPath("$.email").exists());
    }

    @Test
    public void updateShouldReturnStatusNotFoundWhenIdNonExists() throws Exception {

        UUID id = UUID.randomUUID();

        UserDTO dto = Factory.createdUserDtoToUpdateIsNotFound();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/users/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

                result.andExpect(status().isNotFound());

    }

    @Test
    public void deleteByIdShouldDeleteUserByIdWhenIdExists() throws Exception {

        Optional<User> obj = repository.findAll().stream().findFirst();;
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(delete("/users/{id}", id));
                result.andExpect(status().isOk());
    }

    @Test
    public void deleteByIdShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

        UUID id = UUID.randomUUID();

        ResultActions result = mockMvc.perform(delete("/users/{id}", id));
                result.andExpect(status().isNotFound());
    }
}
