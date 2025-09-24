package br.com.gdev.spring_auth_server.model.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public record UserDTO(
        @NotBlank(message = "username must not be blank") String username,
        @NotBlank(message = "password must not be blank") @Size(min = 8, message = "pass must not be less then 8 length") String password,
        @NotBlank(message = "email must not be blank") @Email String email,
        @NotNull(message = "birth must not be null") LocalDate birth,
        MultipartFile file,
        String role

) {
}
