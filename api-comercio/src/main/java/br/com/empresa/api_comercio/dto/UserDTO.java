package br.com.empresa.api_comercio.dto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import br.com.empresa.api_comercio.entities.Role;
import br.com.empresa.api_comercio.entities.User;
import br.com.empresa.api_comercio.validations.EmailConstraint;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class UserDTO extends RepresentationModel<UserDTO> {

	private UUID id;

	@NotNull(message = "The first name field is mandatory.")
	@Size(min = 2, max = 30, message = "Minimum characters allowed are 2 e maximum are 30.")
	private String firstName;

	@NotNull(message = "The last name field is mandatory.")
	@Size(min = 2, max = 50, message = "Minimum characters allowed are 2 e maximum are 50.")
	private String lastName;

	@NotBlank(message = "The email field is mandatory and does not allow blanks.")
	@Email(message = "Email must be of an acceptable standard.")
	@Size(min = 10, max = 50, message = "Minimum characters allowed are 10 e maximum are 50.")
	@EmailConstraint(message = "Email is already in use (not allowed).")
	private String email;

	@Size(min = 7, max = 30, message = "Minimum characters allowed are 7 e maximum are 30.")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	private Set<RoleDTO> roles = new HashSet<>();
	
	public UserDTO() {
	}

	public UserDTO(UUID id, String firstName, String lastName, String email, String password) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public UserDTO(User entity) {
		
		id = entity.getId();
		firstName = entity.getFirstName();
		lastName = entity.getLastName();
		email = entity.getEmail();
		password = entity.getPassword();
	}
	
	public UserDTO(User entity, Set<Role> roles) {
		
		this(entity);
		roles.forEach(x -> this.roles.add(new RoleDTO(x)));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		UserDTO that = (UserDTO) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), id);
	}
}
