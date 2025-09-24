package br.com.gdev.spring_auth_server.infra.exception;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException(String message) {
        super(message);
    }
}
