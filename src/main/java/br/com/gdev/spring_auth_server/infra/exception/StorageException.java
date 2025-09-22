package br.com.gdev.spring_auth_server.infra.exception;

public class StorageException extends RuntimeException {
    public StorageException(String message) {
        super(message);
    }
}
