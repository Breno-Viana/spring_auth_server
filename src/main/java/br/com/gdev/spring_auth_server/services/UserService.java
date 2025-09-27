package br.com.gdev.spring_auth_server.services;

import br.com.gdev.spring_auth_server.model.dtos.*;
import br.com.gdev.spring_auth_server.model.Users;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface UserService {
    public ResponseEntity<UserResponseDTO> registerAccount(UserDTO dto);

    ResponseEntity<UserGetResponseDTO> findUser(String id);

    ResponseEntity<List<UserGetResponseDTO>> findAll();

    Users getUserByEmail(String email);

    ResponseEntity<Void> deleteUser(String id, String email);

    Token authenticate(Login login);

    ResponseEntity<?> getOwn(HttpServletRequest request);
}
