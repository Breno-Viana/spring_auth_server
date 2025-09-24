package br.com.gdev.spring_auth_server.model.dtos;

import java.time.LocalDateTime;

public record ClientResponseDTO(
        String CLIENT_NAME,
        String CLIENT_ID,
        String CLIENT_SCOPE,
        String REDIRECT_URI,
        LocalDateTime CREATE_AT
) {
}
