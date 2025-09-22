package br.com.gdev.spring_auth_server.model.mappers;

import br.com.gdev.spring_auth_server.model.dtos.ClientCreatedResponseDTO;
import br.com.gdev.spring_auth_server.model.dtos.ClientDTO;
import br.com.gdev.spring_auth_server.model.dtos.ClientResponseDTO;
import br.com.gdev.spring_auth_server.model.entities.Client;
import br.com.gdev.spring_auth_server.model.repositories.ClientRepository;
import br.com.gdev.spring_auth_server.security.SecureGenerator;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.UUID;

@Component
public class ClientMapper extends SecureGenerator {


    public Client toEntity(ClientDTO dto) {
        if (dto == null) return new Client();
        Client client = new Client();
        client.setClient_name(dto.client_name());
        client.setScopes(dto.scopes());
        client.setRedirect_uri(dto.redirect_uri());
        client.setClient_id(generateClientId());
        client.setClient_secret(generateClientSecret());
        return client;
    }

    public ClientCreatedResponseDTO toCreatedResponse(Client entity) {
        if (entity == null) return null;

        return new ClientCreatedResponseDTO(
                entity.getClient_name(),
                entity.getClient_id(),
                entity.getClient_secret(),
                entity.getRedirect_uri(),
                entity.getCreate_At(),
                String.format("http://localhost:8989/client/g/%s", entity.getId())
        );
    }

    public ClientResponseDTO toResponse(Client entity) {
        if (entity == null) return null;

        return new ClientResponseDTO(
                entity.getClient_name(),
                entity.getClient_id(),
                entity.getScopes(),
                entity.getRedirect_uri(),
                entity.getCreate_At()
        );
    }


}
