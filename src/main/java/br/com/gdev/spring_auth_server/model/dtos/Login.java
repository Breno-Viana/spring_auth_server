package br.com.gdev.spring_auth_server.model.dtos;

public record Login(
        String username,
        String password
) {
}
