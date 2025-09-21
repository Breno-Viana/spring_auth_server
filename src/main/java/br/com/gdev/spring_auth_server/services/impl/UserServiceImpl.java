package br.com.gdev.spring_auth_server.services.impl;

import br.com.gdev.spring_auth_server.infra.exception.UserNotFoundException;
import br.com.gdev.spring_auth_server.model.dtos.UserDTO;
import br.com.gdev.spring_auth_server.model.dtos.UserGetResponseDTO;
import br.com.gdev.spring_auth_server.model.dtos.UserResponseDTO;
import br.com.gdev.spring_auth_server.model.entities.Users;
import br.com.gdev.spring_auth_server.model.mappers.UserMapper;
import br.com.gdev.spring_auth_server.model.repositories.UserRepository;
import br.com.gdev.spring_auth_server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public ResponseEntity<UserResponseDTO> register_account(UserDTO dto) {
        Users entity = mapper.toEntity(dto);
        String hard_coded_password = dto.password();
        entity.setPass_hash(hard_coded_password);
        Users save = repository.save(entity);
        UserResponseDTO responseDTO = mapper.toResponseDTO(save);
        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<UserGetResponseDTO> find_user(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Users> usr_opt = repository.findById(uuid);
        return usr_opt.map(usr -> {
            UserGetResponseDTO responseDTO = mapper.toGetResponseDTO(usr);
            return ResponseEntity.accepted().body(responseDTO);
        }).orElseThrow(() -> new UserNotFoundException("user not found"));
    }
}
