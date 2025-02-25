package br.com.empresa.api_comercio.entities;

import org.junit.jupiter.api.*;

import java.util.UUID;

public class CategoryTests {

    @Test
    public void categoryShouldHaveCorrectStructure(){

        Category entity = new Category();
        Product product = new Product();

        UUID id = UUID.randomUUID();

        product.setId(id);
        product.setName("Pães");
        product.setPrice(8.0);
        product.setDescription("Pães frescos e crocantes");
        product.setImgUrl("www.img.com");

        entity.setId(id);
        entity.setName("Salgados");

        entity.getProducts().add(product);
        Assertions.assertNotNull(entity.getId());
        Assertions.assertNotNull(entity.getName());
        Assertions.assertNotNull(entity.getProducts());
    }
}
