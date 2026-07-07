package com.archiva.tagtaxonomy.dto;

import java.util.UUID;

public record TagValueResponse(
        UUID id,
        String name,
        String description,
        UUID tagTypeId,
        UUID parentId,
        Long version
) {
}
