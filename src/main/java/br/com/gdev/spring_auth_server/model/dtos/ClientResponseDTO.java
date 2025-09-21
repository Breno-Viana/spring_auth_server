package br.com.gdev.spring_auth_server.model.dtos;

import br.com.gdev.spring_auth_server.model.utils.Scopes;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ClientResponseDTO(
        String CLIENT_NAME,
        String CLIENT_ID,
        Scopes CLIENT_SCOPE,
        String REDIRECT_URI,
        LocalDateTime CREATE_AT
) {
}
