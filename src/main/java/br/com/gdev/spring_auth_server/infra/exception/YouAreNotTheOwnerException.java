package br.com.gdev.spring_auth_server.infra.exception;

public class YouAreNotTheOwnerException extends RuntimeException {
    public YouAreNotTheOwnerException(String message) {
        super(message);
    }
}
