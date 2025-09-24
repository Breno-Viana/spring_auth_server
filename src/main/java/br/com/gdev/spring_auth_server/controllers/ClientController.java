package br.com.gdev.spring_auth_server.controllers;

import br.com.gdev.spring_auth_server.model.dtos.ClientDTO;
import br.com.gdev.spring_auth_server.model.dtos.ClientResponseDTO;
import br.com.gdev.spring_auth_server.services.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService service;

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<?> register_client(@Valid @RequestBody ClientDTO dto){
        return service.register_client(dto);
    }


    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<?> find_clint(@Valid @RequestParam String id){
        return service.find_client(id);
    }

    @GetMapping("/ls")
    @ResponseBody
    public ResponseEntity<List<ClientResponseDTO>> find_all(){
        return service.find_all();
    }
}
