package br.com.gdev.spring_auth_server.services;


import br.com.gdev.spring_auth_server.model.dtos.ClientCreatedResponseDTO;
import br.com.gdev.spring_auth_server.model.dtos.ClientDTO;
import br.com.gdev.spring_auth_server.model.dtos.ClientResponseDTO;
import org.springframework.http.ResponseEntity;

public interface ClientService {
    public ResponseEntity<ClientCreatedResponseDTO> register_client(ClientDTO dto);

    public ResponseEntity<ClientResponseDTO> find_client(String Id);
}
