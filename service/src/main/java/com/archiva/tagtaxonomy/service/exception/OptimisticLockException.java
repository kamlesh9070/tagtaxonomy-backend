package com.archiva.tagtaxonomy.service.exception;

public class OptimisticLockException extends ConflictException {
    public OptimisticLockException(String message) {
        super(message);
    }
}
