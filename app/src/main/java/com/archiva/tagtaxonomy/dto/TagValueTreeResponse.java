package com.archiva.tagtaxonomy.dto;

import java.util.List;
import java.util.UUID;

public record TagValueTreeResponse(
        UUID id,
        String name,
        String description,
        UUID tagTypeId,
        UUID parentId,
        Long version,
        List<TagValueTreeResponse> children
) {
}
