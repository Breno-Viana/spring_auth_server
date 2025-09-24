package br.com.gdev.spring_auth_server.controllers;

import br.com.gdev.spring_auth_server.model.dtos.ClientDTO;
import br.com.gdev.spring_auth_server.model.dtos.ClientResponseDTO;
import br.com.gdev.spring_auth_server.services.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService service;

    @PostMapping("/save")
    public ResponseEntity<?> register_client(@Valid @RequestBody ClientDTO dto){
        return service.register_client(dto);
    }

    @GetMapping("/g/{id}")
    public ResponseEntity<?> find_clint(@Valid @PathVariable String id){
        return service.find_client(id);
    }

    @GetMapping("/ls")
    public ResponseEntity<List<ClientResponseDTO>> find_all(){
        return service.find_all();
    }
}
