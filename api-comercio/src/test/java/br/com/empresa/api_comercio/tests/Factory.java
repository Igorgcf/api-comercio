package br.com.empresa.api_comercio.tests;

import br.com.empresa.api_comercio.dto.*;
import br.com.empresa.api_comercio.entities.*;

import java.util.UUID;

public class Factory {

    public static Category createdCategory(){

        UUID id = UUID.randomUUID();
        Category entity = new Category(id,"Frios");
        return entity;
    }

    public static CategoryDTO createdCategoryDto(){

        UUID id = UUID.randomUUID();
        CategoryDTO dto = new CategoryDTO(id,"Lanches");
        return dto;
    }

    public static CategoryDTO createdCategoryDtoToUpdate(){

        UUID id = UUID.randomUUID();
        CategoryDTO dto = new CategoryDTO(id,"Bebibas");
        return dto;
    }

    public static CategoryDTO createdCategoryDtoToUpdateIsNotFound(){

        UUID id = UUID.randomUUID();
        CategoryDTO dto = new CategoryDTO(id,"Limpeza");
        return dto;
    }

    public static Product createdProduct(){

        UUID id = UUID.randomUUID();
        Product entity = new Product(id,"Leite", 7.77, "Leite integral", "www.img.com");
        return entity;
    }

    public static ProductDTO createdProductDto(){

        UUID id = UUID.randomUUID();
        ProductDTO dto = new ProductDTO(id,"Azeite", 17.77, "De olívia", "www.img.com");
        return dto;
    }

    public static ProductDTO createdProductDtoToUpdate(){

        UUID id = UUID.randomUUID();
        ProductDTO dto = new ProductDTO(id,"Manteiga", 13.99, "Com sal", "www.img.com");
        return dto;
    }

    public static ProductDTO createdProductDtoUpdateIsNotFound(){

        UUID id = UUID.randomUUID();
        ProductDTO dto = new ProductDTO(id,"Biscoito", 3.77, "Chocolate", "www.img.com");
        return dto;
    }

    public static Role createdRole(){

        UUID id = UUID.randomUUID();
        Role entity = new Role(id,"Boss");
        return entity;
    }

    public static RoleDTO createdRoleDto(){

        RoleDTO dto = new RoleDTO(null,"Coordinator");
        return dto;
    }

    public static RoleDTO createdRoleDtoToUpdate(){

        RoleDTO dto = new RoleDTO(null, "Assistant");
        return dto;
    }

    public static RoleDTO createdRoleDtoToUpdateIsNotFound(){

        UUID id = UUID.randomUUID();
        RoleDTO dto = new RoleDTO(id, "CEO");
        return dto;
    }

    public static User createdUser(){

        UUID id = UUID.randomUUID();
        User entity = new User(id, "Bruno", "Abulquerque", "bruno@gmail.com", "123456");
        return entity;
    }

    public static UserDTO createdUserDto(){

        UUID id = UUID.randomUUID();
        UserDTO dto = new UserDTO(id, "Bruno", "Abulquerque", "bruno@gmail.com", "123456");
        return dto;
    }

    public static UserDTO createdUserDtoToUpdate(){

        UUID id = UUID.randomUUID();
        UserDTO dto = new UserDTO(id, "Bruna", "Ferraz", "bruna@gmail.com", "123456");
        return dto;
    }

    public static UserDTO createdUserDtoToUpdateIsNotFound(){

        UUID id = UUID.randomUUID();
        UserDTO dto = new UserDTO(id, "Marcos", "Gonçalves", "marcos@gmail.com", "123456");
        return dto;
    }

}
