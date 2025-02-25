package br.com.empresa.api_comercio.services;

import br.com.empresa.api_comercio.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UserService {

    Page<UserDTO> findAllPaged(Pageable pageable);

    List<UserDTO> queryMethod(String firstName);

    UserDTO findById(UUID id);

    UserDTO insert(UserDTO dto);

    UserDTO update(UUID id, UserDTO dto);

    void deleteById(UUID id);
}
