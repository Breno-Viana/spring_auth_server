package br.com.gdev.spring_auth_server.services;

import br.com.gdev.spring_auth_server.model.dtos.*;
import br.com.gdev.spring_auth_server.model.entities.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface UserService {
    public ResponseEntity<UserResponseDTO> register_account(UserDTO dto);

    ResponseEntity<UserGetResponseDTO> find_user(String id);

    ResponseEntity<List<UserGetResponseDTO>> find_all();

    Users getUserByEmail(String email);

    Token authenticate(Login login);

}
