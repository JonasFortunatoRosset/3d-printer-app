package br.com.bkd.apiprinterapp.exception;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, UUID id) {
        super(resource + " com id " + id + " não existe");
    }
}
