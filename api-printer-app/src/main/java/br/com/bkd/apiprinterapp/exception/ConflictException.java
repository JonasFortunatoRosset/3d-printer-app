package br.com.bkd.apiprinterapp.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
