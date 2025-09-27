package br.com.gdev.spring_auth_server.services;


import br.com.gdev.spring_auth_server.model.dtos.ClientCreatedResponseDTO;
import br.com.gdev.spring_auth_server.model.dtos.ClientDTO;
import br.com.gdev.spring_auth_server.model.dtos.ClientResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ClientService {
    public ResponseEntity<ClientCreatedResponseDTO> register_client(ClientDTO dto, HttpServletRequest request);

    public ResponseEntity<ClientResponseDTO> find_client(String Id);

    public ResponseEntity<List<ClientResponseDTO>> find_all();

    ResponseEntity<Void> delete(String id, HttpServletRequest request);
}
