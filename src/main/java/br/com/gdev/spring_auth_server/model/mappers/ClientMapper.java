package br.com.gdev.spring_auth_server.model.mappers;

import br.com.gdev.spring_auth_server.infra.exception.UserNotFoundException;
import br.com.gdev.spring_auth_server.model.Users;
import br.com.gdev.spring_auth_server.model.dtos.ClientCreatedResponseDTO;
import br.com.gdev.spring_auth_server.model.dtos.ClientDTO;
import br.com.gdev.spring_auth_server.model.dtos.ClientResponseDTO;
import br.com.gdev.spring_auth_server.model.Client;
import br.com.gdev.spring_auth_server.model.repositories.UserRepository;
import br.com.gdev.spring_auth_server.security.utils.SecureGenerator;
import br.com.gdev.spring_auth_server.services.JwtService;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper extends SecureGenerator {


    public Client toEntity(ClientDTO dto, Users owner) {
        if (dto == null) return new Client();
        Client client = new Client();
        client.setClient_name(dto.client_name());
        client.setScopes(dto.scopes());
        client.setRedirect_uri(dto.redirect_uri());
        client.setClient_id(generateClientId());
        client.setClient_secret(generateClientSecret());
        client.setOwner(owner);
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
                String.format("http://localhost:8989/client/search?id=%s", entity.getId())
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
