package br.com.gdev.spring_auth_server.services;

import br.com.gdev.spring_auth_server.model.dtos.UserDTO;
import br.com.gdev.spring_auth_server.model.dtos.UserGetResponseDTO;
import br.com.gdev.spring_auth_server.model.dtos.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    public ResponseEntity<UserResponseDTO> register_account(UserDTO dto);

    ResponseEntity<UserGetResponseDTO> find_user(String id);


    ResponseEntity<List<UserResponseDTO>> find_all();
}
