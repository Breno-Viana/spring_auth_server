package br.com.gdev.spring_auth_server.controllers;

import br.com.gdev.spring_auth_server.model.dtos.ClientDTO;
import br.com.gdev.spring_auth_server.model.dtos.ClientResponseDTO;
import br.com.gdev.spring_auth_server.services.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<?> register_client(@Valid @RequestBody ClientDTO dto, HttpServletRequest request) {
        return service.register_client(dto, request);
    }


    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<?> find_clint(@Valid @RequestParam String id) {
        return service.find_client(id);
    }

    @GetMapping("/ls")
    @ResponseBody
    public ResponseEntity<List<ClientResponseDTO>> find_all() {
        return service.find_all();
    }

    @DeleteMapping("/delete")
//    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> deleteClient(@RequestParam(value = "id") String id, HttpServletRequest request) {
        return service.delete(id, request);
    }
}
