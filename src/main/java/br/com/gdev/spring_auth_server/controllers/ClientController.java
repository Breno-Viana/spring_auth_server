package br.com.gdev.spring_auth_server.controllers;

import br.com.gdev.spring_auth_server.model.dtos.ClientDTO;
import br.com.gdev.spring_auth_server.services.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cl")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService service;

    @PostMapping("/register")
    public ResponseEntity<?> register_client(@Valid @RequestBody ClientDTO dto){
        return service.register_client(dto);
    }

    @GetMapping("/g/{id}")
    public ResponseEntity<?> find_clint(@Valid @PathVariable String id){
        return service.find_client(id);
    }
}
