package com.archiva.tagtaxonomy.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateTagValueRequest(
        @NotBlank(message = "Tag value name is required")
        @Size(max = 100, message = "Tag value name must be at most 100 characters")
        String name,

        @Size(max = 500, message = "Tag value description must be at most 500 characters")
        String description,

        @NotNull(message = "Tag type id is required")
        UUID tagTypeId,

        UUID parentId,

        @NotNull(message = "Version is required")
        Long version
) {
}
