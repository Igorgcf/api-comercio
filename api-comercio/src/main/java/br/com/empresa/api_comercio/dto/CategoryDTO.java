package br.com.empresa.api_comercio.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import br.com.empresa.api_comercio.entities.Category;
import br.com.empresa.api_comercio.entities.Product;
import br.com.empresa.api_comercio.validations.NameConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class CategoryDTO extends RepresentationModel<CategoryDTO> {

	private UUID id;

	@NotNull(message = "The name field is mandatory.")
	@Size(min = 2, max = 30, message = "Minimum characters allowed are 2 and maximum are 30.")
	@NameConstraint(message = "Name already exists (not allowed).")
	private String name;
	
	private List<ProductDTO> products = new ArrayList<>();
	
	public CategoryDTO() {
	}

	public CategoryDTO(UUID id, String name) {
		
		this.id = id;
		this.name = name;
	}
	
	public CategoryDTO(Category entity) {
		
		id = entity.getId();
		name = entity.getName();
	}

	public CategoryDTO(Category entity, List<Product> products) {
		this(entity);
		products.forEach(x -> this.products.add(new ProductDTO(x)));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		CategoryDTO that = (CategoryDTO) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), id);
	}
}
