package br.com.gdev.spring_auth_server.services.impl;

import br.com.gdev.spring_auth_server.infra.exception.ClientNotFoundExeption;
import br.com.gdev.spring_auth_server.model.dtos.ClientCreatedResponseDTO;
import br.com.gdev.spring_auth_server.model.dtos.ClientDTO;
import br.com.gdev.spring_auth_server.model.dtos.ClientResponseDTO;
import br.com.gdev.spring_auth_server.model.entities.Client;
import br.com.gdev.spring_auth_server.model.mappers.ClientMapper;
import br.com.gdev.spring_auth_server.model.repositories.ClientRepository;
import br.com.gdev.spring_auth_server.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthClientService implements ClientService {
    private final ClientRepository repository;
    private final ClientMapper mapper;


    @Override
    public ResponseEntity<ClientCreatedResponseDTO> register_client(ClientDTO dto) {
        Client entity = mapper.toEntity(dto);
        Client save = repository.save(entity);
        ClientCreatedResponseDTO createdResponse = mapper.toCreatedResponse(save);
        return ResponseEntity.ok(createdResponse);
    }

    @Override
    public ResponseEntity<ClientResponseDTO> find_client(String Id) {
        Optional<Client> client = repository.findById(UUID.fromString(Id));
        return client.map(cl ->{
            ClientResponseDTO response = mapper.toResponse(cl);
            return ResponseEntity.accepted().body(response);
        }).orElseThrow(() -> new ClientNotFoundExeption("client not founded"));
    }

    @Override
    public ResponseEntity<List<ClientResponseDTO>> find_all() {
        List<Client> all = repository.findAll();
        List<ClientResponseDTO> list = all.stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(list);
    }
}
