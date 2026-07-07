package com.archiva.tagtaxonomy.validation;

public final class ValidationUtils {
    private ValidationUtils() {
    }

    public static String normalizeText(String value) {
        return value == null ? null : value.trim();
    }
}
