package br.com.gdev.spring_auth_server.infra.exception;

public class StorageLocationException extends RuntimeException {
    public StorageLocationException(String message) {
        super(message);
    }
}
