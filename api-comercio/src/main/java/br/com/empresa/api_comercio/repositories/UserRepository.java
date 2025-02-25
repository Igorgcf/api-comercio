package br.com.empresa.api_comercio.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.empresa.api_comercio.entities.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{

	List<User> findAllByFirstNameContainingIgnoreCase(@Param("firstName") String firstName);

	boolean existsByEmail(String email);
}
