package br.com.empresa.api_comercio.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.empresa.api_comercio.dto.*;
import br.com.empresa.api_comercio.entities.Category;
import br.com.empresa.api_comercio.repositories.*;
import br.com.empresa.api_comercio.services.exception.ResourceNotFoundException;
import br.com.empresa.api_comercio.services.impl.CategoryServiceImpl;
import br.com.empresa.api_comercio.tests.*;
import com.fasterxml.jackson.databind.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryResourceIT {

    @Autowired
    private CategoryServiceImpl service;

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void findAllPagedShouldReturnAllCategoriesSortByName() throws Exception{

        PageRequest pageable = PageRequest.of(0,12);

        ResultActions result = mockMvc.perform(get("/categories?sort=name"));

        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].id").exists());
        result.andExpect(jsonPath("$.content[0].name").exists());
    }

    @Test
    public void queryMethodShouldReturnAllCategoryFilteredByName() throws Exception {

        String name = "Doc";

        ResultActions result = mockMvc.perform(get("/categories/name/{name}", name));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.[0].id").exists());
        result.andExpect(jsonPath("$.[0].name").exists());
    }

    @Test
    public void queryMethodShouldReturnStatusNotFoundWhenNameNonExisting() throws Exception {

        String name = "Salgados";

        ResultActions result = mockMvc.perform(get("/categories/name/{name}", name));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExisting() throws Exception{

        Optional<Category> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(get("/categories/{id}", id));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void findByIdShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

        UUID id = UUID.randomUUID();

        ResultActions result = mockMvc.perform(get("/categories/{id}", id));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure() throws Exception {

        CategoryDTO dto = Factory.createdCategoryDto();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/categories")
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
                            result.andExpect(status().isOk());
    }

    @Test
    public void updateShouldSaveObjectWhenIdExisting() throws Exception {

        Optional<Category> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        CategoryDTO dto = Factory.createdCategoryDtoToUpdate();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/categories/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

                result.andExpect(status().isOk());
                result.andExpect(jsonPath("$.id").exists());
                result.andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void updateShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

        UUID id = UUID.randomUUID();

        CategoryDTO dto = Factory.createdCategoryDtoToUpdateIsNotFound();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/categories/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteByIdShouldReturnBadRequestWhenIdIsAssociated() throws Exception {

        Optional<Category> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(delete("/categories/{id}", id));
                result.andExpect(status().isBadRequest());
    }

    @Test
    public void deleteByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting() throws Exception {

        UUID id = UUID.randomUUID();

        ResultActions result = mockMvc.perform(delete("/categories/{id}", id));
                result.andExpect(status().isNotFound());
    }
}
