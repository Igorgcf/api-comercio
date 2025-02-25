package br.com.empresa.api_comercio.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.empresa.api_comercio.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.empresa.api_comercio.dto.RoleDTO;
import br.com.empresa.api_comercio.entities.Role;
import br.com.empresa.api_comercio.repositories.RoleRepository;
import br.com.empresa.api_comercio.services.exception.ResourceNotFoundException;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository repository;

	@Transactional(readOnly = true)
	@Override
	public Page<RoleDTO> findAllPaged(Pageable pageable) {

		Page<Role> page = repository.findAll(pageable);

		return page.map(RoleDTO::new);
	}

	@Transactional(readOnly = true)
	@Override
	public List<RoleDTO> queryMethod(String authority){
		
		List<Role> list = repository.findAllByAuthorityContainingIgnoreCase(authority);
		if(list.isEmpty()){
			throw new ResourceNotFoundException("Name not found: " + authority);
		}
		return list.stream().map(RoleDTO::new).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	@Override
	public RoleDTO findById(UUID id) {

		Optional<Role> obj = repository.findById(id);
		Role entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));

		return new RoleDTO(entity);
	}

	@Transactional
	@Override
	public RoleDTO insert(RoleDTO dto) {

		Role entity = new Role();
		entity.setAuthority(dto.getAuthority());
		repository.save(entity);
		return new RoleDTO(entity);
	}

	@Transactional
	@Override
	public RoleDTO update(UUID id, RoleDTO dto) {

		Optional<Role> obj = repository.findById(id);

		Role entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
		entity.setAuthority(dto.getAuthority());
		repository.save(entity);
		return new RoleDTO(entity);
	}

	@Override
	public void deleteById(UUID id) {


		Optional<Role> obj = repository.findById(id);
		if (obj.isEmpty()) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}
		repository.deleteById(id);
	}
}
