package br.com.gdev.spring_auth_server.services.impl;

import br.com.gdev.spring_auth_server.infra.exception.ClientNotFoundExeption;
import br.com.gdev.spring_auth_server.infra.exception.UserNotFoundException;
import br.com.gdev.spring_auth_server.infra.exception.YouAreNotTheOwnerException;
import br.com.gdev.spring_auth_server.model.Users;
import br.com.gdev.spring_auth_server.model.dtos.*;
import br.com.gdev.spring_auth_server.model.Client;
import br.com.gdev.spring_auth_server.model.mappers.ClientMapper;
import br.com.gdev.spring_auth_server.model.repositories.ClientRepository;
import br.com.gdev.spring_auth_server.model.repositories.UserRepository;
import br.com.gdev.spring_auth_server.security.utils.SecurityInformationGetter;
import br.com.gdev.spring_auth_server.services.ClientService;
import br.com.gdev.spring_auth_server.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationAuthClientServerService implements ClientService, SecurityInformationGetter {
    private final ClientRepository repository;
    private final ClientMapper mapper;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public ResponseEntity<ClientCreatedResponseDTO> register_client(ClientDTO dto, HttpServletRequest request) {
        String token = recoveryBearerToken(request);
        Subject subject = jwtService.getSubject(new Token(null, token));
        Users owner = userRepository.findByEmail(subject.subject()).orElseThrow(()-> new UserNotFoundException("User not found"));
        Client entity = mapper.toEntity(dto, owner);
        Client save = repository.save(entity);
        ClientCreatedResponseDTO createdResponse = mapper.toCreatedResponse(save);
        return ResponseEntity.ok(createdResponse);
    }

    @Override
    public ResponseEntity<ClientResponseDTO> find_client(String Id) {
        Optional<Client> client = repository.findById(Id);
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

    @Override
    public ResponseEntity<Void> delete(String id, HttpServletRequest request) {
        String token = recoveryBearerToken(request);
        Subject subject = jwtService.getSubject(new Token(null, token));
        Optional<Client> client = repository.findById(id);
        Optional<Users> user = userRepository.findByEmail(subject.subject());

        client.ifPresent(cl -> {
            user.ifPresent(us ->{
                if (cl.getOwner().equals(us)){
                    repository.delete(cl);
                }else {
                    throw new YouAreNotTheOwnerException("you're not the owner");
                }
            });
        });

        return ResponseEntity.noContent().build();
    }
}
