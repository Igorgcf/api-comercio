package br.com.empresa.api_comercio.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.empresa.api_comercio.dto.*;
import br.com.empresa.api_comercio.entities.Product;
import br.com.empresa.api_comercio.repositories.ProductRepository;
import br.com.empresa.api_comercio.services.exception.ResourceNotFoundException;
import br.com.empresa.api_comercio.services.impl.ProductServiceImpl;
import br.com.empresa.api_comercio.tests.*;
import com.fasterxml.jackson.databind.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductResourceIT {

    @Autowired
    private ProductServiceImpl service;

    @Autowired
    private ProductRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void findAllPagedShouldReturnAllProductsSortByName() throws Exception {

        ResultActions result = mockMvc.perform(get("/products?sort=name"));
            result.andExpect(status().isOk());

            result.andExpect(jsonPath("$.content").exists());
            result.andExpect(jsonPath("$.content[0].id").exists());
            result.andExpect(jsonPath("$.content[0].name").exists());
            result.andExpect(jsonPath("$.content[0].price").exists());
            result.andExpect(jsonPath("$.content[0].description").exists());
            result.andExpect(jsonPath("$.content[0].imgUrl").exists());
    }

    @Test
    public void queryMethodShouldReturnAllProductFilteredByName() throws Exception {

        String name = "Bolo";

        ResultActions result = mockMvc.perform(get("/products/name/{name}", name));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.[0].id").exists());
        result.andExpect(jsonPath("$.[0].name").exists());
        result.andExpect(jsonPath("$.[0].price").exists());
        result.andExpect(jsonPath("$.[0].description").exists());
        result.andExpect(jsonPath("$.[0].imgUrl").exists());
    }

    @Test
    public void queryMethodShouldReturnStatusNotFoundWhenNameNonExisting() throws Exception {

        String name = "Torta de amora";

        ResultActions result = mockMvc.perform(get("/products/name/{name}", name));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExisting() throws Exception {

        Optional<Product> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(get("/products/{id}", id));

            result.andExpect(status().isOk());
            result.andExpect(jsonPath("$.id").exists());
            result.andExpect(jsonPath("$.name").exists());
            result.andExpect(jsonPath("$.price").exists());
            result.andExpect(jsonPath("$.description").exists());
            result.andExpect(jsonPath("$.imgUrl").exists());
    }

    @Test
    public void findByIdShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

        UUID id = UUID.randomUUID();

        ResultActions result = mockMvc.perform(get("/products/{id}",id));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure() throws Exception{

        ProductDTO dto = Factory.createdProductDto();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/products")
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

                result.andExpect(status().isOk());
                result.andExpect(jsonPath("$.id").exists());
                result.andExpect(jsonPath("$.name").exists());
                result.andExpect(jsonPath("$.price").exists());
                result.andExpect(jsonPath("$.description").exists());
                result.andExpect(jsonPath("$.imgUrl").exists());
    }

    @Test
    public void updateShouldSaveObjectWhenIdExisting() throws Exception{

        Optional<Product> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ProductDTO dto = Factory.createdProductDtoToUpdate();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/products/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

                result.andExpect(status().isOk());
                result.andExpect(jsonPath("$.id").exists());
                result.andExpect(jsonPath("$.name").exists());
                result.andExpect(jsonPath("$.price").exists());
                result.andExpect(jsonPath("$.description").exists());
                result.andExpect(jsonPath("$.imgUrl").exists());
    }

    @Test
    public void updateShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

        UUID id = UUID.randomUUID();

        ProductDTO dto = Factory.createdProductDtoUpdateIsNotFound();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/products/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

                result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteByIdShouldDeleteProductByIdWhenIdExists() throws Exception {

        Optional<Product> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(delete("/products/{id}", id));
                result.andExpect(status().isOk());
    }

    @Test
    public void deleteByIdShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

        UUID id = UUID.randomUUID();

        ResultActions result = mockMvc.perform(delete("/products/{id}", id));
                result.andExpect(status().isNotFound());
    }
}
