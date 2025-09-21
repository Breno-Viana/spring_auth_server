package br.com.gdev.spring_auth_server.model.dtos;

import java.time.LocalDate;

public record UserResponseDTO(
        String USERNAME,
        String EMAIL,
        String GET_URL
) {
}
