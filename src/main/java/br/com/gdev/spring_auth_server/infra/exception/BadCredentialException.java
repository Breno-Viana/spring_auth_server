package br.com.gdev.spring_auth_server.infra.exception;

public class BadCredentialException extends RuntimeException {
    public BadCredentialException(String message) {
        super(message);
    }
}
