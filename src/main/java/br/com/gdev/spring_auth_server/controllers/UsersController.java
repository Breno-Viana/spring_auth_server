package br.com.gdev.spring_auth_server.controllers;

import br.com.gdev.spring_auth_server.model.dtos.UserDTO;
import br.com.gdev.spring_auth_server.model.dtos.UserResponseDTO;
import br.com.gdev.spring_auth_server.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsersController {
    private final UserService service;

    @PostMapping(value = "/save", consumes = {"multipart/form-data"})
    public ResponseEntity<?> register_account(@Valid @ModelAttribute UserDTO dto){
        return service.register_account(dto);
    }


    @GetMapping("/g/{id}")
    public ResponseEntity<?> find_user(@PathVariable("id") String id){
        return service.find_user(id);
    }

    @GetMapping("/ls")
    public ResponseEntity<List<UserResponseDTO>> find_all(){
        return service.find_all();
    }


}
