package com.pgvpt.exception;

public class PatrimoineNotFoundException extends RuntimeException {

    public PatrimoineNotFoundException(String message) {
        super(message);
    }

    public PatrimoineNotFoundException(String resourceName, Object id) {
        super(resourceName + " introuvable avec l'identifiant : " + id);
    }
}
