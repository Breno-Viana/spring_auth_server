package br.com.gdev.spring_auth_server.infra.exception;

public class ClientNotFoundExeption extends RuntimeException {
    public ClientNotFoundExeption(String message) {
        super(message);
    }
}
