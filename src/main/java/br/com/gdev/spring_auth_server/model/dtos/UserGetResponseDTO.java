package br.com.gdev.spring_auth_server.model.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserGetResponseDTO(
        String USERNAME,
        String EMAIL,
        LocalDate BIRTH,
        Long AGE,
        LocalDateTime CREATE_AT
) {
}
