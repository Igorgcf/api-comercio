package br.com.empresa.api_comercio.entities;

import org.junit.jupiter.api.*;

import java.util.UUID;

public class ProductTests {

    @Test
    public void productShouldHaveCorrectStructure() {

        Product entity = new Product();
        Category category = new Category();

        UUID id = UUID.randomUUID();

        category.setId(id);
        category.setName("Doces");

        entity.setId(id);
        entity.setName("Pão sonho");
        entity.setPrice(5.0);
        entity.setDescription("Sabor doce de leite");
        entity.setImgUrl("www.img.com");

        entity.getCategories().add(category);

        Assertions.assertNotNull(entity);
        Assertions.assertNotNull(entity.getCategories());

    }
}
