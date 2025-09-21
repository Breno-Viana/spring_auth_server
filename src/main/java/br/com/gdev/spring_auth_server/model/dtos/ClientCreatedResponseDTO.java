package br.com.gdev.spring_auth_server.model.dtos;

import java.time.LocalDateTime;

public record ClientCreatedResponseDTO(
    String CLIENT_NAME,
    String CLIENT_ID,
    String CLIENT_SECRET,
    String REDIRECT_URI,
    LocalDateTime CREATED_AT,
    String CONSULT_URL
) {

}
