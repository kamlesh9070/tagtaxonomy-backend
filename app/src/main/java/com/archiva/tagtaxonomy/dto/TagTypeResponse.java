package com.archiva.tagtaxonomy.dto;

import java.util.UUID;

public record TagTypeResponse(
        UUID id,
        String name,
        String description,
        boolean isMultiValue,
        boolean isRequired,
        Long version
) {
}
