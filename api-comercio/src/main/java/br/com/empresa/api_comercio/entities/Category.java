package br.com.empresa.api_comercio.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_category")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(nullable = false)
	private String name;
	
	@ManyToMany(mappedBy = "categories", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Product> products = new ArrayList<>();
	
	public Category() {
	}

	public Category(UUID id, String name) {
		this.id = id;
		this.name = name;
	}

}
