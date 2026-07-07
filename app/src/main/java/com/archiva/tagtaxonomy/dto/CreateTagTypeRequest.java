package com.archiva.tagtaxonomy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTagTypeRequest(
        @NotBlank(message = "Tag type name is required")
        @Size(max = 100, message = "Tag type name must be at most 100 characters")
        String name,

        @Size(max = 500, message = "Tag type description must be at most 500 characters")
        String description,

        boolean isMultiValue,

        boolean isRequired
) {
}
