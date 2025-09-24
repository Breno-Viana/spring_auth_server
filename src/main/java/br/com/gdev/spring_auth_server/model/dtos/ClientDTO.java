package br.com.gdev.spring_auth_server.model.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClientDTO (
        @NotBlank String client_name,
        @NotBlank String redirect_uri,
        @NotNull String scopes

){
}
