package br.com.gdev.spring_auth_server.infra.exception;

public class AlreadyOwnerException extends RuntimeException {
    public AlreadyOwnerException(String message) {
        super(message);
    }
}
